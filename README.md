# shopmall
springboot ecommerce with jpa and secrurities for browser user and api user 

jakarta validator 는 api 이며 Hibernate validator 가 구현한다. spring 이 기동될때 bean 으로 등록
custom validator 를 구현하면 (@ValidEmail) , Hibernate validator 를 확장한것이 등록된다. (기본 @NotNull 등 체크)
이 bean 은 @Valid 로 구동되거나 entity persist 될때 구동된다. 수동으로 주입받아서 .validate 메소드를 호출할수도 있다.  
spring validator api 는 별도의 빈으로 등록할수 있다. 구동방법은 org.springframework.validation.Validator 타입의 빈을 수동으로 주입받아 사용,또는 InitBinder 사용   
spring validator 는 다른 spring validator 를 주입받아 하나의 global validator 로 사용가능하며, hibernate validator 도 주입받아 사용가능 (ex, BookValidator)    
  
initBinder 는 @ModelAttribue 객체에만 적용됨. @RequestBody 에 적용하려면 jackson deserializer 를 사용해야한다.

