package io.audium.audiumbackend.config;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class AudiumMySQL5Dialect extends MySQL5Dialect {

    public AudiumMySQL5Dialect() {
        super();
        //registerFunction("GROUP_CONCAT", new StandardSQLFunction("GROUP_CONCAT", new StringType()));
        registerFunction("GROUP_CONCAT", new StandardSQLFunction("GROUP_CONCAT", StandardBasicTypes.STRING));
    }
}
