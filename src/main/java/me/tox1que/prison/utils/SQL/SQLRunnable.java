package me.tox1que.prison.utils.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLRunnable{
    void run(ResultSet resultSet) throws SQLException;
}
