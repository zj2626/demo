package com.demo.common.service.thread.abs;

public abstract class MyExcutor implements ExcutorInterface {

    @Override
    public abstract Object doExcute() throws Exception;

    @Override
    public Object doExcuteRead() throws Exception {
        return "error";
    }


    //    private LocalDateTime begin;
    //    private LocalDateTime end;
    //
    //    @Before
    //    public void before() {
    //        begin = LocalDateTime.now();
    //    }
    //
    //    @After
    //    public void after() {
    //        end = LocalDateTime.now();
    //        Duration duration = Duration.between(begin, end);
    //        System.out.println("consequence: " + duration.toMillis());
    //    }
}
