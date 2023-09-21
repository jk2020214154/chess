package com.chess.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.chess.backend.consumer.utils.JwtAuthentication;
import com.chess.backend.mapper.RecordMapper;
import com.chess.backend.mapper.UserMapper;
import com.chess.backend.pojo.User;
import com.chess.backend.utils.Algorithm.Entrance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    private final static ConcurrentHashMap<Integer, WebSocketServer > users = new ConcurrentHashMap<>();
    private User user;
    private Session session = null;
    private static UserMapper userMapper;

    public static RecordMapper recordMapper;
    public static JSONObject data;


    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session=session;
        System.out.println("connected");
        Integer userId= JwtAuthentication.getUserId(token);
        this.user=userMapper.selectById(userId);
        if(this.user!=null) {
            users.put(userId, this);
        }
        else{
            this.session.close();
        }
        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnected");
        if(this.user!=null){
            users.remove(this.user.getId());
        }
    }
    public static void startGame(Integer userid,Integer difficulty) {
        try {
            System.out.println("userid:"+userid);
            Entrance entrance=new Entrance();
            String from_to=entrance.fun(data.getString("fen"),difficulty,userid);
            String from=from_to.substring(0,2);
            String to=from_to.substring(2,4);
            System.out.println(from+" "+to);
            JSONObject respGame=new JSONObject();
            respGame.put("id",userid);
            respGame.put("from",from);
            respGame.put("to",to);
            if(users.get(userid)!=null)
            {
                users.get(userid).sendMessage(respGame.toJSONString());
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    private void move(int direction) {
        System.out.println("move " + direction);
    }

    @OnMessage
    public void onMessage(String message, Session session){
        //从Client接收消息
        System.out.println("receive message!");
        System.out.println(message+"----");
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/chess?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
            String username = "root";
            String password = "mzw123";
            conn = DriverManager.getConnection(url, username, password);
            //System.out.println("correct connect!!!!!!!!!!");
            // 连接成功
        }
        catch (SQLException e) {
            // 连接失败
            e.printStackTrace();
        }
        //System.out.println(message);
        data = JSONObject.parseObject(message);
        Integer repentance=Integer.valueOf(data.getInteger("repentance"));
        System.out.println("!!repentance:"+repentance);
        //System.out.println(data);
        Integer userid=null;
        //System.out.println(data.getString("difficulty"));
        if(data.getString("id")!=null)
            userid=Integer.valueOf(data.getString("id"));
        Integer difficulty=Integer.valueOf(data.getString("difficulty"));
        Integer changecnt=Integer.valueOf(data.getString("cnt"));
        String FEN=String.valueOf(data.getString("fen"));
        if(repentance==1)
        {
            String needcurrent="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
            ResultSet resultSet = null;
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            String sql1 = "SELECT * FROM record WHERE user_id = ? ORDER BY id DESC LIMIT 2,1";
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8",
                        "root",
                        "mzw123");
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setInt(1,userid);
                resultSet = preparedStatement.executeQuery();
                // 处理查询结果
                if (resultSet.next()) {
                    // 从resultSet中获取数据
                    Integer userIds = resultSet.getInt("user_id");
                    String current = resultSet.getString("current");
                    needcurrent = current;
                    System.out.println("user_id: " + userIds + ", current: " + current);
                }
                else {
                    System.out.println("没有找到符合条件的记录");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                // 关闭ResultSet、Statement和连接
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            for(int i=1;i<=2;i++)
            {
                String sqlSelect = "SELECT id FROM record WHERE user_id = ? ORDER BY id DESC LIMIT 1";
                connection = null;
                PreparedStatement selectStatement = null;
                resultSet = null;
                int idToDelete = -1; // 存储要删除的记录的id
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8",
                            "root",
                            "mzw123");
                    selectStatement = connection.prepareStatement(sqlSelect);
                    selectStatement.setInt(1, userid);
                    resultSet = selectStatement.executeQuery();

                    if (resultSet.next()) {
                        idToDelete = resultSet.getInt("id");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                        if (selectStatement != null) {
                            selectStatement.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                String sqlDelete = "DELETE FROM record WHERE id = ?";
                PreparedStatement deleteStatement = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8",
                            "root",
                            "mzw123");
                    deleteStatement = connection.prepareStatement(sqlDelete);
                    deleteStatement.setInt(1, idToDelete);
                    int rowsAffected = deleteStatement.executeUpdate();
                    System.out.println("成功删除 " + rowsAffected + " 行记录");
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (deleteStatement != null) {
                            deleteStatement.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            JSONObject respcurrent=new JSONObject();
            respcurrent.put("newfen",needcurrent);
            sendMessage(respcurrent.toJSONString());
            return ;
        }
        if(changecnt==1)
        {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO record (user_id,current,target) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userid);
                stmt.setString(2, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
                stmt.setString(3, "1");
                System.out.println("!!!!!!"+"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"+"!!!!!!");
                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(changecnt%2==1)
        {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO record (user_id,current,target) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userid);
                stmt.setString(2, FEN);
                stmt.setString(3, "1");
                System.out.println("!!!!!!"+FEN+"!!!!!!");
                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            startGame(userid,difficulty);
        }
        else
        {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO record (user_id,current,target) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userid);
                stmt.setString(2, FEN);
                stmt.setString(3, "1");
                System.out.println("!!!!!!"+FEN+"!!!!!!");
                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("!changecnt:"+changecnt);
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message){
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}