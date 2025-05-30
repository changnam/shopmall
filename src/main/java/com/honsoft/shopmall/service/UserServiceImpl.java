package com.honsoft.shopmall.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import com.honsoft.shopmall.entity.UserRoleAssignment;
import com.honsoft.shopmall.entity.UserRoleAssignmentHistory;
import com.honsoft.shopmall.mapper.RoleMapper;
import com.honsoft.shopmall.mapper.UserMapper;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.repository.UserRepository;
import com.honsoft.shopmall.repository.UserRoleAssignmentHistoryRepository;
import com.honsoft.shopmall.repository.UserRoleAssignmentRepository;
import com.honsoft.shopmall.repository.UserRoleHistoryRepository;
import com.honsoft.shopmall.request.UserCreateRequest;
import com.honsoft.shopmall.request.UserRoleAssignmentRequest;
import com.honsoft.shopmall.request.UserUpdateRequest;

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
//	private final UserRoleRepository userRoleRepository;
	private final UserRoleHistoryRepository historyRepository;
	private final UserRoleAssignmentRepository userRoleAssignmentRepository;
	private final UserRoleAssignmentHistoryRepository userRoleAssignmentHistoryRepository;
	private final UserMapper userMapper;
	private final RoleMapper roleMapper;
	private final Validator validator;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(EntityManager entityManager, BizExceptionMessageService bizExceptionMessageService,
			UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper,
			@Qualifier("bookValidator") Validator validator, PasswordEncoder passwordEncoder,
//			UserRoleRepository userRoleRepository,
			UserRoleHistoryRepository historyRepository, UserRoleAssignmentRepository userRoleAssignmentRepository,
			UserRoleAssignmentHistoryRepository userRoleAssignmentHistoryRepository) {
		this.entityManger = entityManager;
		this.bizExceptionMessageService = bizExceptionMessageService;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
		this.validator = validator;
		this.passwordEncoder = passwordEncoder;
//		this.userRoleRepository = userRoleRepository;
		this.historyRepository = historyRepository;
		this.userRoleAssignmentRepository = userRoleAssignmentRepository;
		this.userRoleAssignmentHistoryRepository = userRoleAssignmentHistoryRepository;
	}

	@Transactional
//	@Override
	public User signup(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public UserDto createUser(UserCreateRequest request) {
		// Check if user already exists
		if (userRepository.existsById(request.getUserId())) {
			throw new IllegalArgumentException("User with ID already exists.");
		}

//		User user = new User();
//		user.setUserId(request.getUserId());
//		user.setPassword(request.getPassword()); // You may encode password
//		user.setName(request.getName());
//		user.setEmail(request.getEmail());
//		user.setEnabled(request.getEnabled());

		User user = userMapper.toEntity(request);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		List<UserRoleAssignment> assignments = new ArrayList<>();
		List<UserRoleAssignmentHistory> assignmentsHistory = new ArrayList<>();

		for (String roleId : request.getRoleIds()) {
			Role role = roleRepository.findById(roleId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid role: " + roleId));

			UserRoleAssignment assignment = new UserRoleAssignment();
			assignment.setUser(user);
			assignment.setRole(role);
			assignments.add(assignment);

			// Record history
			UserRoleAssignmentHistory history = new UserRoleAssignmentHistory();
			history.setUserId(user.getUserId());
			history.setRoleId(role.getRoleId());
			history.setAction("ASSIGNED");
			assignmentsHistory.add(history);

		}

		user.setRoleAssignments(assignments);

		User saveUser = userRepository.save(user);

		userRoleAssignmentHistoryRepository.saveAll(assignmentsHistory);

//		        return user.getUserId();
		return userMapper.toDto(saveUser);
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
	public UserDto updateUser(String userId, UserUpdateRequest userUpdateRequest) {
		logger.info("@@@@@@@@@@@@@@@@@@@ 1");
		briefOverviewOfPersistentContextContext();
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));

		logger.info("@@@@@@@@@@@@@@@@@@@ 2");
		briefOverviewOfPersistentContextContext();
		userMapper.updateEntity(userUpdateRequest, existingUser);

//		User updatedUser = userRepository.save(existingUser);
		// Convert incoming roleIds to a Set for efficient lookup
//		Set<String> incomingRoleIds = new HashSet<>();
		if (userUpdateRequest.getRoleIds() != null) {
			Set<String> incomingRoleIds = new HashSet<>(userUpdateRequest.getRoleIds());

			checkRolesExist(incomingRoleIds);

			Iterator<UserRoleAssignment> iterator = existingUser.getRoleAssignments().iterator();
			while (iterator.hasNext()) {
				UserRoleAssignment assignment = iterator.next();
				if (!incomingRoleIds.contains(assignment.getRole().getRoleId())) {

					// Record history first
					UserRoleAssignmentHistory history = new UserRoleAssignmentHistory();
					history.setUserId(existingUser.getUserId());
					history.setRoleId(assignment.getRole().getRoleId());
					history.setAction("REMOVED");
					userRoleAssignmentHistoryRepository.save(history);

					assignment.getRole().getRoleAssignments().remove(assignment);
					assignment.setUser(null); // break bidirectional link
					assignment.setRole(null);
					iterator.remove(); // safe way to remove from collection
				}
			}

			// Load current assignments
			List<UserRoleAssignment> currentAssignments = userRoleAssignmentRepository
					.findByUser_UserId(existingUser.getUserId());

//		// Find roles to be removed
//		for (UserRoleAssignment assignment : currentAssignments) {
//			String existingRoleId = assignment.getRole().getRoleId();
//			if (!incomingRoleIds.contains(existingRoleId)) {
//				userRoleAssignmentRepository.delete(assignment);
//
//				// Record history
//				UserRoleAssignmentHistory history = new UserRoleAssignmentHistory();
//				history.setUserId(updatedUser.getUserId());
//				history.setRoleId(existingRoleId);
//				history.setAction("REMOVED");
//				userRoleAssignmentHistoryRepository.save(history);
//			}
//		}

			// Add new roles
			List<Role> roles = roleRepository.findAllById(incomingRoleIds);
			Set<String> existingRoleIds = currentAssignments.stream().map(ura -> ura.getRole().getRoleId())
					.collect(Collectors.toSet());

			for (Role role : roles) {
				if (!existingRoleIds.contains(role.getRoleId())) {
					UserRoleAssignment newAssignment = new UserRoleAssignment();
					newAssignment.setUser(existingUser);
					newAssignment.setRole(role);
//				userRoleAssignmentRepository.save(newAssignment);
					existingUser.addRoleAssignment(newAssignment);

					// Record history
					UserRoleAssignmentHistory history = new UserRoleAssignmentHistory();
					history.setUserId(existingUser.getUserId());
					history.setRoleId(role.getRoleId());
					history.setAction("ASSIGNED");
					userRoleAssignmentHistoryRepository.save(history);
				}
			}
		}
//		
//		logger.info("@@@@@@@@@@@@@@@@@@@ 3");
//		briefOverviewOfPersistentContextContext();
//		existingUser.clearRoles();
//		logger.info("@@@@@@@@@@@@@@@@@@@ 4");
//		briefOverviewOfPersistentContextContext();
//		
//		if (userUpdateRequest.getRoleIds() != null) {
//			for (String roleId : userUpdateRequest.getRoleIds()) {
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

		User updatedUser = userRepository.save(existingUser);

		return userMapper.toDto(updatedUser);
//		return null;
	}

	@Transactional
	@Override
	public void deleteUserById(String userId) {
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId));

		List<UserRoleAssignmentHistory> assignmentsHistory = new ArrayList<>();
		for (UserRoleAssignment ura : existingUser.getRoleAssignments()) {

			// Record history
			UserRoleAssignmentHistory history = new UserRoleAssignmentHistory();
			history.setUserId(existingUser.getUserId());
			history.setRoleId(ura.getRole().getRoleId());
			history.setAction("REMOVED");
			assignmentsHistory.add(history);
		}

		userRepository.delete(existingUser);
		userRoleAssignmentHistoryRepository.saveAll(assignmentsHistory);
	}

	@Transactional
	@Override
	public void assignRoleToUser(String userId, String roleId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
//		userRole.setId(new UserRoleId(role.getRoleId(), user.getUserId()));
		userRole.setAssignedAt(LocalDateTime.now());

		user.getRoleAssignments().add(null);
		userRepository.save(user); // Handles join table automatically
	}

	@Transactional
	@Override
	public void removeRoleFromUser(String userId, String roleId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		user.getRoleAssignments().removeIf(userRole -> userRole.getRole().getRoleId().equals(roleId));
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

	private org.hibernate.engine.spi.PersistenceContext getPersistenceContext() {
		SharedSessionContractImplementor sharedSession = entityManger.unwrap(SharedSessionContractImplementor.class);
		return sharedSession.getPersistenceContext();
	}

	private void briefOverviewOfPersistentContextContext() {
		org.hibernate.engine.spi.PersistenceContext persistenceContext = getPersistenceContext();
		int managedEntities = persistenceContext.getNumberOfManagedEntities();
		int collectionEntriesSize = persistenceContext.getCollectionEntriesSize();
		logger.info("Total numbe of managed entities: {}", managedEntities);
		logger.info("Total numbe of collection entries  {}", collectionEntriesSize);

		Map<EntityKey, Object> entitiesByKey = persistenceContext.getEntitiesByKey();
		if (!entitiesByKey.isEmpty()) {
			logger.info("\nEntities by key: ");
			entitiesByKey.forEach((key, value) -> logger.info("{} : {}", key, value));

			logger.info("\nStatus and hydrated state: ");
			for (Map.Entry<EntityKey, Object> entry : entitiesByKey.entrySet()) {
				Object entity = entry.getValue(); // The actual entity object
				EntityEntry ee = persistenceContext.getEntry(entity); // ✅ Correct method
				logger.info("Entity name: {} | Status: {} | State: {}", ee.getEntityName(), ee.getStatus(),
						Arrays.toString(ee.getLoadedState()));
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

	@Transactional
	@Override
	public void assignRoleToUser(String roleId, String userId, String assignedBy) {
		User user = userRepository.findById(userId).orElseThrow();
		Role role = roleRepository.findById(roleId).orElseThrow();

		// Check if already assigned
//		boolean exists = userRoleRepository.existsByUserAndRole(user, role);
//		if (!exists) {
//			userRoleRepository.save(new UserRole(user, role, LocalDateTime.now(), assignedBy, true));
//			historyRepository.save(new UserRoleHistory(user, role, LocalDateTime.now(), ChangeType.ADDED, assignedBy));
//		}
	}

	@Transactional
	@Override
	public void assignRoles(UserRoleAssignmentRequest request) {
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Convert incoming roleIds to a Set for efficient lookup
		Set<String> incomingRoleIds = new HashSet<>(request.getRoleIds());

		checkRolesExist(incomingRoleIds);

		// Load current assignments
		List<UserRoleAssignment> currentAssignments = userRoleAssignmentRepository.findByUser_UserId(user.getUserId());

		// Find roles to be removed
		for (UserRoleAssignment assignment : currentAssignments) {
			String existingRoleId = assignment.getRole().getRoleId();
			if (!incomingRoleIds.contains(existingRoleId)) {
				userRoleAssignmentRepository.delete(assignment);

				// Record history
				UserRoleAssignmentHistory history = new UserRoleAssignmentHistory();
				history.setUserId(user.getUserId());
				history.setRoleId(existingRoleId);
				history.setAction("REMOVED");
				userRoleAssignmentHistoryRepository.save(history);
			}
		}

		// Add new roles
		List<Role> roles = roleRepository.findAllById(incomingRoleIds);
		Set<String> existingRoleIds = currentAssignments.stream().map(ura -> ura.getRole().getRoleId())
				.collect(Collectors.toSet());

		for (Role role : roles) {
			if (!existingRoleIds.contains(role.getRoleId())) {
				UserRoleAssignment newAssignment = new UserRoleAssignment();
				newAssignment.setUser(user);
				newAssignment.setRole(role);
				userRoleAssignmentRepository.save(newAssignment);

				// Record history
				UserRoleAssignmentHistory history = new UserRoleAssignmentHistory();
				history.setUserId(user.getUserId());
				history.setRoleId(role.getRoleId());
				history.setAction("ASSIGNED");
				userRoleAssignmentHistoryRepository.save(history);
			}
		}
	}

	private void checkRolesExist(Set<String> incomingRoleIds) {
		for (String roleId : incomingRoleIds) {
			roleRepository.findById(roleId)
					.orElseThrow(() -> new EntityNotFoundException("role " + roleId + " not found"));
		}

	}

}
