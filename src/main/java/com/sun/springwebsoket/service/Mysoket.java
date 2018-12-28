package com.sun.springwebsoket.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ken
 * @date 2018/12/27  22:21
 * @description
 */
@ServerEndpoint("/message")
@Component
public class Mysoket {

    //当前的在线人数
    private static AtomicInteger count=new AtomicInteger(0);

    public  static CopyOnWriteArraySet<Mysoket> set=new CopyOnWriteArraySet<>();


    private Session session;


    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session=session;
        set.add(this);
        count.addAndGet(1);
        System.out.println(session.getId()+"登录了");
        System.out.println("当前的在线人数"+count.get());
        pushAll("1:"+count.get(),this.session);
    }

    @OnMessage
    public void sendMessage(String message,Session session) throws IOException {
        System.out.println("来着客户端的消息："+message);
        pushAll("3:"+message,this.session);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onclose() throws IOException {
        boolean remove = set.remove(this);
      if(remove)
      {
          count.decrementAndGet();
          System.out.println(session.getId()+"用户退出了");
          pushAll("2:"+count.get(),this.session);
      }
    }

    public static void pushAll(String message,Session session) throws IOException {
        Iterator<Mysoket> iterator = set.iterator();
        while (iterator.hasNext())
        {
            Mysoket next = iterator.next();

            next.session.getBasicRemote().sendText(message);
        }
    }




}
