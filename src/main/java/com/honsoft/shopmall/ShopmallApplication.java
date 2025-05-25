package com.honsoft.shopmall;

import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.entity.User;
import com.honsoft.shopmall.mapper.CustomerMapper;
import com.honsoft.shopmall.mapper.PermissionMapper;
import com.honsoft.shopmall.mapper.RoleMapper;
import com.honsoft.shopmall.mapper.UserMapper;
import com.honsoft.shopmall.repository.AddressRepository;
import com.honsoft.shopmall.repository.BookRepositoryManual;
import com.honsoft.shopmall.repository.CustomerRepository;
import com.honsoft.shopmall.repository.MemberRepository;
import com.honsoft.shopmall.repository.PermissionRepository;
import com.honsoft.shopmall.repository.PersonRepository;
import com.honsoft.shopmall.repository.RoleRepository;
import com.honsoft.shopmall.repository.UserRepository;
import com.honsoft.shopmall.request.RoleCreateDto;
import com.honsoft.shopmall.service.RoleService;
import com.honsoft.shopmall.service.UserService;
import com.honsoft.shopmall.util.FullyQualifiedBeanNameGenerator;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class,
		UserDetailsServiceAutoConfiguration.class })
@ComponentScan(nameGenerator = FullyQualifiedBeanNameGenerator.class)
public class ShopmallApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ShopmallApplication.class);

	private final BookRepositoryManual bookRepository;
	private final PersonRepository personRepository;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final CustomerRepository customerRepository;
	private final AddressRepository addressRepository;
	private final CustomerMapper customerMapper;
	private final UserService userService;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final RoleService roleService;
	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;
	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;

	public ShopmallApplication(BookRepositoryManual bookRepository, PersonRepository personRepository,
			MemberRepository memberRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository,
			AddressRepository addressRepository, CustomerMapper customerMapper, UserService userService,
			UserRepository userRepository, UserMapper userMapper, RoleService roleService, RoleRepository roleRepositoy,
			RoleMapper roleMapper, PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
		this.bookRepository = bookRepository;
		this.personRepository = personRepository;
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
		this.customerMapper = customerMapper;
		this.userService = userService;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleService = roleService;
		this.roleRepository = roleRepositoy;
		this.roleMapper = roleMapper;
		this.permissionRepository = permissionRepository;
		this.permissionMapper = permissionMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(ShopmallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		Book book1 = new Book();
//		book1.setBookId("ISBN1234");
//		book1.setName ("자바스크립트 입문");
//		book1.setUnitPrice(new BigDecimal(30000));
//		book1.setAuthor("조현영");
//		book1.setDescription(
//				"자바스크립트의 기초부터 심화까지 핵심 문법을 학습한 후 12가지 프로그램을 만들며 학습한 내용을 확인할 수 있습니다. 문법 학습과 실습이 적절히 섞여 있어 프로그램을 만드는 방법을 재미있게 익힐 수 있습니다.");
//		book1.setPublisher("길벗");
//		book1.setCategory("IT전문서");
//		book1.setUnitsInStock(1000);
//		book1.setReleaseDate("2024/02/20");
//		book1.setFileName("ISBN1234.jpg");
//		
//		Book book2 = new Book();
//		book2.setBookId("ISBN1235");
//		book2.setName ("파이썬의 정석");
//		book2.setUnitPrice(new BigDecimal(29800));
//		book2.setAuthor("조용주,임좌상");
//		book2.setDescription(
//				"4차 산업혁명의 핵심인 머신러닝, 사물 인터넷(IoT), 데이터 분석 등 다양한 분야에 활용되는 직관적이고 간결한 문법의 파이썬 프로그래밍 언어를 최신 트렌드에 맞게 예제 중심으로 학습할 수 있습니다.");
//		book2.setPublisher("길벗");
//		book2.setCategory("IT교육교재");
//		book2.setUnitsInStock(1000);
//		book2.setReleaseDate("2023/01/10");
//		book2.setFileName("ISBN1235.jpg");
//		
//		Book book3 = new Book();
//		book3.setBookId("ISBN1236");
//		book3.setName ("안드로이드 프로그래밍");
//		book3.setUnitPrice(new BigDecimal(36000));
//		book3.setAuthor("송미영");
//		book3.setDescription(
//				"안드로이드의 기본 개념을 체계적으로 익히고, 이를 실습 예제를 통해 익힙니다. 기본 개념과 사용법을 스스로 실전에 적용하는 방법을 학습한 다음 실습 예제와 응용 예제를 통해 실전 프로젝트 응용력을 키웁니다.");
//		book3.setPublisher("길벗");
//		book3.setCategory("IT교육교재");
//		book3.setUnitsInStock(1000);
//		book3.setReleaseDate("2023/06/30");
//		book3.setFileName("ISBN1236.jpg");
////		
//		bookRepository.setNewBook(book1);
//		bookRepository.setNewBook(book2);
//		bookRepository.setNewBook(book3);
//		
////		Person person = new Person();
////		person.setName("홍길동");
////		person.setPassword("1234");
////		
////		personRepository.save(person);
//		
//		Member member = new Member();
//		member.setAddress("버드나루로130 (강변래미안 302동 904호)");
//		member.setEmail("cngoh@honsoft.com");
//		member.setMemberId("cngoh");
//		member.setName("changnam go");
//		member.setPassword(passwordEncoder.encode("password"));
//		member.setPhone("010");
//		member.setRole(Role.USER);s
//		
//		memberRepository.save(member);

//		Customer customer1 = customerRepository.findById("cngoh").orElse(null);
//		if (customer1 == null) {
//			CustomerDto customerDto = CustomerDto.builder().customerId("cngoh").name("changnam").build();
//			customer1 = customerMapper.toEntity(customerDto);
//			Address address1 = Address.builder().addressName("home").country("seoul").customer(customer1).build();
//			customer1.getAddresses().add(address1);
//			Address address2 = Address.builder().addressName("office").country("jeju").customer(customer1).build();
//			customer1.getAddresses().add(address2);
//			customerRepository.save(customer1);
//		}
//
//		Customer customer2 = customerRepository.findById("ykgoh").orElse(null);
//		if (customer2 == null) {
//			CustomerDto customerDto = CustomerDto.builder().customerId("ykgoh").name("youngkyung").build();
//			customer2 = customerMapper.toEntity(customerDto);
//			Address address1 = Address.builder().addressName("home").country("seoul").customer(customer2).build();
//			customer2.getAddresses().add(address1);
//			Address address2 = Address.builder().addressName("office").country("seoul").customer(customer2).build();
//			customer2.getAddresses().add(address2);
//			customerRepository.save(customer2);
//		}
//		api_admin_001, /api/admin/**, "api admin get", "GET"
//		Permission permission = permissionRepository.findById("api_admin_001").orElse(null);
//		if (permission == null) {
//			PermissionDto permissionDto = PermissionDto.builder().permissionId("api_admin_001").path("/api/admin/**").name("api admin get").httpMethod("GET").build();
//			permission = permissionMapper.toEntity(permissionDto);
//			permissionRepository.save(permission);
//		}
//		
//		Role role = roleRepository.findById("admin").orElse(null);
//		if (role == null) {
//			RoleCreateDto roleCreateDto = new RoleCreateDto();
//			roleCreateDto.setRoleId("admin");
//			roleCreateDto.setRoleName("ROLE_ADMIN");
//			RoleDto adminRole = roleService.createRole(roleCreateDto);
//			logger.info("admin role created");
//		}
//
////		User user = userRepository.findById("cngoh").orElse(null);
////		if (user == null) {
////			UserDto userDto = UserDto.builder().userId("cngoh").password(passwordEncoder.encode("pass")).build();
////			user = userMapper.toEntity(userDto);
////			userRepository.save(user);
////		}
//
//		User admin = userRepository.findById("admin").orElse(null);
//		if (admin == null) {
////			UserCreateDto adminCreateDto = new UserCreateDto();
////			adminCreateDto.setUserId("admin");
////			adminCreateDto.setEmail("admin@honsoft.com");
////			adminCreateDto.setPassword(passwordEncoder.encode("password"));
////			adminCreateDto.setRoleIds(new HashSet<>(List.of("admin")));
////			UserDto adminDto = userService.createUser(adminCreateDto);
//			
//			User adminUser = new User();
//			adminUser.setUserId("admin");
//			adminUser.setEmail("admin@honsoft.com");
//			adminUser.setPassword(passwordEncoder.encode("password"));
////			admin.setUserRoles(new HashSet<>(List.of("")));
//			userRepository.save(adminUser);
//		}
//		logger.info("admin user created");
	}

	@Autowired
	private ApplicationContext context;

	@PostConstruct
	public void listBeans() {
		System.out.println("🔄 Application restarted at " + Instant.now());
		System.out.println("============> jakarta validator");
		Map<String, jakarta.validation.Validator> beans = context.getBeansOfType(jakarta.validation.Validator.class);
		beans.forEach((name, bean) -> System.out.println(name + " → " + bean.getClass().getSimpleName()));
		System.out.println("============> spring validator");
		Map<String, org.springframework.validation.Validator> springBeans = context
				.getBeansOfType(org.springframework.validation.Validator.class);
		springBeans.forEach((name, bean) -> System.out.println(name + " → " + bean.getClass().getSimpleName()));

	}

}
