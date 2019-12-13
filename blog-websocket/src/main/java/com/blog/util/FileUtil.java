package com.blog.util;

import com.blog.config.ConfigProperties;
import com.blog.consts.WebSocketConsts;
import com.blog.dto.SendChatMessageReqt;
import com.blog.vo.FileContentVo;
import com.blog.vo.SaveChatMessageVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 */
@Component
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    @Autowired
    private ConfigProperties configProperties;

    /**
     * 文件类型：系统日志，公聊，群聊，私聊
     * @return
     * @throws IOException
     */
    public File createFile(String fileName) throws IOException {
        String dateUrl = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String folderUrl = configProperties.getFileBaseUrl() + dateUrl + "\\";
        File foler =new File(folderUrl);
        if (!foler.exists()){ // 判断当前日期文件夹是否存在
            //foler.mkdir();// 只会建立一级的文件夹
            foler.mkdirs();// 创建路径下所有文件夹
            // file.isDirectory()； //判断是不是一个文件夹
        }
        String fileUrl = folderUrl + fileName + ".txt";
        File file =new File(fileUrl);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public void writeObj(File file,String fileName, Object obj) throws IOException {
//        boolean isexit = false;
        if (file == null) {
            file = createFile(fileName);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file,true);
//        if (file.exists()) {
//            isexit = true;// 序列化文件存在,追加内容
//        }
//
//        // 每次new的时候都会写入一个StreamHeader,所以要把屁股后面的StreamHeader去掉
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//        long pos = 0;// 可以说是文件的长度
//        if (isexit) {
//            // getChannel()返回此通道的文件位置，这是一个非负整数，它计算从文件的开始到当前位置之间的字节数
//            pos = fileOutputStream.getChannel().position()-4;// TODO 第一次不要减，之后的都要减4 StreamHeader有4个字节所以减去
//            // 将此通道的文件截取为给定大小
//            fileOutputStream.getChannel().truncate(pos);
//            log.debug("追加成功~");
//        }
//        objectOutputStream.writeObject(obj.toString());
//         //关闭流
//        objectOutputStream.close();

        OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream, "utf-8");

        String writeStr = MessageFormat.format("{0}:{1}\n",
                DateUtil.toString(DateUtil.getCurDate(), DateUtil.DATE_PATTERN_YYYYMMDDHHmmSS),
                obj.toString());
        osw.write(writeStr);
        osw.flush();
        osw.close();
        fileOutputStream.close();
    }

    public static List<FileContentVo> readObj(File file){
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        List<FileContentVo> list = new ArrayList<FileContentVo>();
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (fileInputStream.available() > 0) {
                list.add((FileContentVo) objectInputStream.readObject());
            }
            log.debug("读取~");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @Async
    public void saveChatMessage(SendChatMessageReqt sendChatMessageReqt) throws Exception {
        String fileName = sendChatMessageReqt.getToId();
        if (StringUtils.equals(sendChatMessageReqt.getMessageType(), WebSocketConsts.MESSAGE_TYPE_SYSTEM)) {
            fileName = WebSocketConsts.SYSTEM_FILE_NAME;
        }

        SaveChatMessageVo saveChatMessageVo = new SaveChatMessageVo();
        saveChatMessageVo.setSendDate(DateUtil.getCurDate());
        saveChatMessageVo.setSendMessage(sendChatMessageReqt.getSendMessage());
        saveChatMessageVo.setSendName(WebSocketInfoUtil.chatInfoMap.get(sendChatMessageReqt.getSendSid()).getName());
        saveChatMessageVo.setSendSid(sendChatMessageReqt.getSendSid());
        this.writeObj(null, fileName, saveChatMessageVo);
    }
}
