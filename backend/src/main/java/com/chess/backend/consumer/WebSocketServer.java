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

    public static void startGame(Integer userid) {
        try {

            System.out.println("userid:"+userid);
            Entrance entrance=new Entrance();


            String from_to=entrance.fun(data.getString("fen"));

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
        // 从Client接收消息
        System.out.println("receive message!");
        System.out.println(message+"----");
//        System.out.println(message);
        data = JSONObject.parseObject(message);
        //System.out.println(data);

        Integer userid=null;

        if(data.getString("id")!=null)
            userid=Integer.valueOf(data.getString("id"));
        startGame(userid);
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