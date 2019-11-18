package hello.service.proxy;

public interface PolicyService<P, R> {

    R execute(P param);
}
