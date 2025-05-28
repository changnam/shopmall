package com.honsoft.shopmall.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.engine.spi.CollectionEntry;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import com.honsoft.shopmall.dto.UserDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.entity.UserRole;
import com.honsoft.shopmall.entity.UserRoleId;
import com.honsoft.shopmall.mapper.RoleMapper;
import com.honsoft.shopmall.mapper.UserMapper;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.repository.UserRepository;
import com.honsoft.shopmall.request.UserCreateDto;
import com.honsoft.shopmall.request.UserUpdateDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@PersistenceContext
	private final EntityManager entityManger;
	
	private final BizExceptionMessageService bizExceptionMessageService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserMapper userMapper;
	private final RoleMapper roleMapper;
	private final Validator validator;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(EntityManager entityManager,BizExceptionMessageService bizExceptionMessageService,UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper,
			@Qualifier("bookValidator") Validator validator,PasswordEncoder passwordEncoder) {
		this.entityManger = entityManager;
		this.bizExceptionMessageService = bizExceptionMessageService;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
		this.validator = validator;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
//	@Override
	public User signup(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public UserDto createUser(UserCreateDto userCreateDto) {
//		briefOverviewOfPersistentContextContext();
		User user = userMapper.toEntity(userCreateDto);
		//check email already exists
		userRepository.findByEmail(user.getEmail()).ifPresent(a -> {throw bizExceptionMessageService.createLocalizedException("EMAIL_ALREADY_EXIST");});

		user.setPassword(passwordEncoder.encode(user.getPassword()));		
		User savedUser = userRepository.save(user);
		UserDto savedUserDto = userMapper.toDto(savedUser);
		briefOverviewOfPersistentContextContext();
		return savedUserDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user -> {
			return userMapper.toDto(user);
		}).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));
		UserDto userDto = userMapper.toDto(user);
		return userDto;
	}

	@Transactional
	@Override
	public UserDto updateUser(String userId, UserUpdateDto userUpdateDto) {
		logger.info("@@@@@@@@@@@@@@@@@@@ 1");
		briefOverviewOfPersistentContextContext();
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));
		
		logger.info("@@@@@@@@@@@@@@@@@@@ 2");
		briefOverviewOfPersistentContextContext();
		userMapper.updateEntity(userUpdateDto, existingUser);
//		
//		logger.info("@@@@@@@@@@@@@@@@@@@ 3");
//		briefOverviewOfPersistentContextContext();
//		existingUser.clearRoles();
//		logger.info("@@@@@@@@@@@@@@@@@@@ 4");
//		briefOverviewOfPersistentContextContext();
//		
//		if (userUpdateDto.getRoleIds() != null) {
//			for (String roleId : userUpdateDto.getRoleIds()) {
//				Role role = roleRepository.findById(roleId)
//						.orElseThrow(() -> new EntityNotFoundException(roleId + " not found"));
//				existingUser.addRole(role);
//			}
//		}
//		
//		logger.info("@@@@@@@@@@@@@@@@@@@ 5");
//		briefOverviewOfPersistentContextContext();
//		entityManger.flush();
//		logger.info("@@@@@@@@@@@@@@@@@@@ 6");
//		briefOverviewOfPersistentContextContext();
//		User updatedUser = userRepository.save(existingUser);
//		logger.info("@@@@@@@@@@@@@@@@@@@ 6-1");
//		briefOverviewOfPersistentContextContext();
		return userMapper.toDto(existingUser);
//		return null;
	}

	@Transactional
	@Override
	public void deleteUserById(String userId) {
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));
		userRepository.deleteById(userId);		
	}

	@Transactional
	@Override
	public void assignRoleToUser(String userId, String roleId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    Role role = roleRepository.findById(roleId)
	        .orElseThrow(() -> new RuntimeException("Role not found"));

	    UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        userRole.setId(new UserRoleId(role.getRoleId(), user.getUserId()));
        userRole.setAssignedAt(LocalDateTime.now());
        
	    user.getUserRoles().add(userRole);
	    userRepository.save(user);  // Handles join table automatically
	}
	
	@Transactional
	@Override
	public void removeRoleFromUser(String userId, String roleId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    user.getUserRoles().removeIf(userRole -> userRole.getRole().getRoleId().equals(roleId));
	    userRepository.save(user);
	}

	@Override
	public Page<UserDto> getPageUsers(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		Page<UserDto> dtos = userMapper.toPage(users);
		return dtos;
	}

	@Override
	public Optional<UserDto> findById(String id) {
		 return userRepository.findById(id).map(userMapper::toDto);
	}

	private org.hibernate.engine.spi.PersistenceContext getPersistenceContext(){
		SharedSessionContractImplementor sharedSession = entityManger.unwrap(SharedSessionContractImplementor.class);
		return sharedSession.getPersistenceContext();
	}

	private void briefOverviewOfPersistentContextContext() {
		org.hibernate.engine.spi.PersistenceContext persistenceContext = getPersistenceContext();
		int managedEntities = persistenceContext.getNumberOfManagedEntities();
		int collectionEntriesSize = persistenceContext.getCollectionEntriesSize();
		logger.info("Total numbe of managed entities: {}",managedEntities);
		logger.info("Total numbe of collection entries  {}",collectionEntriesSize);
		
		Map<EntityKey, Object> entitiesByKey = persistenceContext.getEntitiesByKey();
		if(!entitiesByKey.isEmpty()) {
			logger.info("\nEntities by key: ");
			entitiesByKey.forEach((key, value) -> logger.info("{} : {}",key,value ));
			
			logger.info("\nStatus and hydrated state: ");
			for (Map.Entry<EntityKey, Object> entry : entitiesByKey.entrySet()) {
			    Object entity = entry.getValue(); // The actual entity object
			    EntityEntry ee = persistenceContext.getEntry(entity); // ✅ Correct method
			    logger.info("Entity name: {} | Status: {} | State: {}",
			        ee.getEntityName(), ee.getStatus(), Arrays.toString(ee.getLoadedState()));
			}
//			
////			for (Object entry : entitiesByKey.values()) {
////				EntityEntry ee = persistenceContext.getEntity(entry.getKey());
////				logger.info("Entity name: {} | Status: {} | State: {}",ee.getEntityName(),ee.getStatus(),Arrays.toString(ee.getLoadedState()));			
////			}
		}
		
//		if (collectionEntriesSize > 0) {
//			logger.info("\nCollection entries:");
//			persistenceContext.forEachCollectionEntry((k, v)->logger.info("Key: {}, Value: {}",k,v.getRole() == null ? "" : v),false);
//		}
		
		if (collectionEntriesSize > 0) {
		    logger.info("\nCollection entries:");

		    Map<Object, CollectionEntry> copy = new HashMap<>();
		    persistenceContext.forEachCollectionEntry(copy::put, false);

		    for (Map.Entry<Object, CollectionEntry> entry : copy.entrySet()) {
		        Object key = entry.getKey();
		        CollectionEntry value = entry.getValue();
		        logger.info("Key: {}, Value: {}", key, value.getRole() == null ? "" : value);
		    }
		}

		
	}

}
