package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.Strictness;
import jakarta.persistence.Table;
import org.example.converter.Converter;
import org.example.model.dto.AbstractDTO;
import org.example.model.entity.AbstractEntity;
import org.example.pattern.Constants;
import org.example.utils.HibernateUtil;
import org.example.utils.IgnoreFieldExclusionStrategy;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.UUID;

public abstract class AbstractServlet<E extends AbstractEntity> extends HttpServlet {

    public String getJsonString(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonBuilder.append(line);
        }
        String jsonRequest = jsonBuilder.toString();
        return jsonRequest;
    }

    public Gson getGjon() {
        return new com.google.gson.GsonBuilder()
                .disableHtmlEscaping()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.FINAL)
                .setExclusionStrategies(new IgnoreFieldExclusionStrategy())
                .setDateFormat(Constants.GSON_DATE_FORMAT)
                .setStrictness(Strictness.LENIENT)
                .create();
    }

    public void write(HttpServletResponse response, E entity, Class<? extends AbstractDTO> dtoClass) {
        try {
            AbstractDTO dto = Converter.toDto(entity, dtoClass);
            response.getWriter().write(getGjon().toJson(dto));
        }catch (Exception e) {
            throw new RuntimeException("Erro ao retornar dados");
        }
    }

    public E findById(UUID id) {
        Class<E> entityClass = getEntityClass();

        String tableName = getTableName(entityClass);

        return searchByIdInDatabase(tableName, id);
    }

    public E searchByIdInDatabase(String tableName, UUID id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            String stringSql = "select * from ".concat(tableName).concat(" where id = '").concat(id.toString()).concat("'");

            transaction.commit();

            Query<E> query = session.createNativeQuery(stringSql, getEntityClass());

            E singleResult = query.getSingleResult();

//            initializeLazyCollections(singleResult);

            return singleResult;
        }
    }
    protected UUID getId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        String[] parts = pathInfo.split("/");

        return UUID.fromString(parts[1]);

    }

    private Class<E> getEntityClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();

        return (Class<E>) genericTypes[0];
    }

    private String getTableName(Class<E> entityClass) {
        if(entityClass.isAnnotationPresent(Table.class)) {
            return entityClass.getAnnotation(Table.class).name();
        }
        throw new RuntimeException("Nao foi possivel encontrar o nome da tabela");
    }


}
