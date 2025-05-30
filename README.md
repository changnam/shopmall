# shopmall
springboot ecommerce with jpa and secrurities for browser user and api user 

jakarta validator 는 api 이며 Hibernate validator 가 구현한다. spring 이 기동될때 bean 으로 등록
custom validator 를 구현하면 (@ValidEmail) , Hibernate validator 를 확장한것이 등록된다. (기본 @NotNull 등 체크)
이 bean 은 @Valid 로 구동되거나 entity persist 될때 구동된다. 수동으로 주입받아서 .validate 메소드를 호출할수도 있다.  
spring validator api 는 별도의 빈으로 등록할수 있다. 구동방법은 org.springframework.validation.Validator 타입의 빈을 수동으로 주입받아 사용,또는 InitBinder 사용   
spring validator 는 다른 spring validator 를 주입받아 하나의 global validator 로 사용가능하며, hibernate validator 도 주입받아 사용가능 (ex, BookValidator)    
  
initBinder 는 @ModelAttribue 객체에만 적용됨. @RequestBody 에 적용하려면 jackson deserializer 를 사용해야한다. trim을 위해 각각 GlobalAdvice 의 @InitBinder 와 custom deserializer 빈을 등록
  
spring security filter 를 통과해서 (anonymous 포함) controller method 로 들어가면 이후 exception 처리는 try-catch 에서 처리하거나 controllerAdvice 에서 처리하거나 spring default exception handler 에게 위임한다.  
  
controller method 로 넘어가기 전에 발생하는 에러는 AuthenticationEntryPoint 또는 AccessDeniedHandler 에서 처리됨  
  
ControllerAdvice 가 여러 개이면 가장 specific 한 exception handler 를 찾는다. 따라서 Exception.class 에 대해서 GlobalAdvice 와 GlobalControllerAdvice 에 설정되어 있다면 Ambiguous @ExceptionHandler method error 발생하므로 @Order(1) 과 @Order(2) 로 구분해 놓을것 , NoHandlerFoundException 과 NoResourceFoundException 은 controller 에서 내는 exception 이 아니므로 GlobalAdvice 에서 처리해야 한다.
  
@EnableConfigurationPorperties 는 @ConfigurationProperties 어노테이션이 된 클래스를 빈으로 등록하고자 할때 사용, 단, 해당 클래스에 @Component 가 되어 있거나 @Bean 으로 등록되었다면 불필요함. @Value 대신 사용해야 하는 이유는 1. 값이 여기 저기 분산 가능성 2.No validation 3. Lack of immutability 4. Manual defaulting (기본값 설정이 가능하여 예측불확실)   
  
jpa 에서 bidirectional 인 경우 반드시, collection 에서 제거 및 link 를 끊어줘야 orphanremoval 이 동작함.  
    assignment.getRole().getRoleAssignments().remove(assignment);  <--- role 의 collection 에서 제거  
                assignment.setUser(null); // break bidirectional link  
                assignment.setRole(null); // break bidirectional link   
                iterator.remove();        <--- user 의 collection 에서 제거  
그리고, 중요한 사항은 persistence context 내의 변경사항 전체가 commit 될때 반영된다.   
  
  


  

