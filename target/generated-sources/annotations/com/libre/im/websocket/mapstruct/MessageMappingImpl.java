package com.libre.im.websocket.mapstruct;

import com.libre.im.websocket.message.TextMessage;
import com.libre.im.websocket.proto.TextMessageProto;
import com.libre.im.web.pojo.ChatMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-30T00:31:23+0800",
    comments = "version: 1.5.0.RC1, compiler: javac, environment: Java 1.8.0_321 (Oracle Corporation)"
)
public class MessageMappingImpl implements MessageMapping {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    @Override
    public TextMessageProto.TextMessage targetToSource(TextMessage arg0) {
        if ( arg0 == null ) {
            return null;
        }

        TextMessageProto.TextMessage.Builder textMessage = TextMessageProto.TextMessage.newBuilder();

        if ( arg0.getCreateTime() != null ) {
            textMessage.setCreateTime( dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168.format( arg0.getCreateTime() ) );
        }
        if ( arg0.getId() != null ) {
            textMessage.setId( arg0.getId() );
        }
        if ( arg0.getConnectType() != null ) {
            textMessage.setConnectType( arg0.getConnectType() );
        }
        if ( arg0.getMessageBodyType() != null ) {
            textMessage.setMessageBodyType( arg0.getMessageBodyType() );
        }
        if ( arg0.getSendUserId() != null ) {
            textMessage.setSendUserId( arg0.getSendUserId() );
        }
        if ( arg0.getStatus() != null ) {
            textMessage.setStatus( arg0.getStatus() );
        }
        textMessage.setBody( arg0.getBody() );
        if ( arg0.getAcceptUserId() != null ) {
            textMessage.setAcceptUserId( arg0.getAcceptUserId() );
        }
        if ( arg0.getAcceptGroupId() != null ) {
            textMessage.setAcceptGroupId( arg0.getAcceptGroupId() );
        }

        return textMessage.build();
    }

    @Override
    public List<TextMessage> sourceToTarget(List<TextMessageProto.TextMessage> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<TextMessage> list = new ArrayList<TextMessage>( arg0.size() );
        for ( TextMessageProto.TextMessage textMessage : arg0 ) {
            list.add( sourceToTarget( textMessage ) );
        }

        return list;
    }

    @Override
    public List<TextMessageProto.TextMessage> targetToSource(List<TextMessage> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<TextMessageProto.TextMessage> list = new ArrayList<TextMessageProto.TextMessage>( arg0.size() );
        for ( TextMessage textMessage : arg0 ) {
            list.add( targetToSource( textMessage ) );
        }

        return list;
    }

    @Override
    public List<TextMessage> sourceToTarget(Stream<TextMessageProto.TextMessage> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        return arg0.map( textMessage -> sourceToTarget( textMessage ) )
        .collect( Collectors.toCollection( ArrayList<TextMessage>::new ) );
    }

    @Override
    public List<TextMessageProto.TextMessage> targetToSource(Stream<TextMessage> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        return arg0.map( textMessage -> targetToSource( textMessage ) )
        .collect( Collectors.toCollection( ArrayList<TextMessageProto.TextMessage>::new ) );
    }

    @Override
    public TextMessage sourceToTarget(TextMessageProto.TextMessage textMessage) {
        if ( textMessage == null ) {
            return null;
        }

        TextMessage textMessage1 = new TextMessage();

        if ( textMessage.getCreateTime() != null ) {
            textMessage1.setCreateTime( LocalDateTime.parse( textMessage.getCreateTime(), dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168 ) );
        }
        textMessage1.setId( textMessage.getId() );
        textMessage1.setConnectType( textMessage.getConnectType() );
        textMessage1.setMessageBodyType( textMessage.getMessageBodyType() );
        textMessage1.setSendUserId( textMessage.getSendUserId() );
        textMessage1.setStatus( textMessage.getStatus() );
        textMessage1.setBody( textMessage.getBody() );
        textMessage1.setAcceptUserId( textMessage.getAcceptUserId() );
        textMessage1.setAcceptGroupId( textMessage.getAcceptGroupId() );

        return textMessage1;
    }

    @Override
    public ChatMessage convertToChatMessage(TextMessage textMessage) {
        if ( textMessage == null ) {
            return null;
        }

        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setMessage( textMessage.getBody() );
        chatMessage.setId( textMessage.getId() );
        chatMessage.setSendUserId( textMessage.getSendUserId() );
        chatMessage.setAcceptUserId( textMessage.getAcceptUserId() );
        chatMessage.setStatus( textMessage.getStatus() );
        chatMessage.setCreateTime( textMessage.getCreateTime() );

        return chatMessage;
    }
}
