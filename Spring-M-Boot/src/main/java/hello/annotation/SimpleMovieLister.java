package hello.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class SimpleMovieLister {
    private String simpleName;

    @Autowired
    private MovieRecommender movieRecommender;

    public MovieRecommender getMovieRecommender() {
        return movieRecommender;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public void setMovieRecommender(MovieRecommender movieRecommender) {
        this.movieRecommender = movieRecommender;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"simpleName\":\"")
                .append(simpleName).append('\"');
        sb.append(",\"movieRecommender\":")
                .append(movieRecommender);
        sb.append('}');
        return sb.toString();
    }
}
