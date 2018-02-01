package com.datcent;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

/**
 * @author JacX.
 * @Description:文件下载
 * @date 2018/1/31 9:34
 * @since 1.0
 */

@Component
@RequestMapping("/downloadController")
public class DownloadController {

    /**
     * 文件下载命令
     * @param srcFile 下载的文件
     * @return
     */
    @RequestMapping("downloadFile")
    public ResponseEntity downloadFile(@RequestParam("srcFile") String srcFile) {
        try {
            String dfileName = new String(srcFile.getBytes("gb2312"), "utf-8");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", dfileName);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(srcFile)), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 移动文件命令
     * @param mvFile 移动到的文件夹
     * @param absolutePath 需要移动的文件
     * @return
     */
    @RequestMapping("mvFile")
    public @ResponseBody String moveFile(@RequestParam("absolutePath") String absolutePath,@RequestParam("mvFile")String mvFile){
        File mvFiles = new File(mvFile);
        if(!mvFiles.exists())
            mvFiles.mkdir();
        String cmd = "mv " + absolutePath  + " " + mvFile;
        return command(cmd);
    }

    /**
     * 返回指定文件夹目录下当前所有文件
     * @param srcFile
     * @return
     */
    @RequestMapping("listFiles")
    public @ResponseBody String listFiles(@RequestParam("srcFile") String srcFile){
        File srcFiles = new File(srcFile);
        File[] sonFiles = srcFiles.listFiles();
        StringBuilder files = new StringBuilder();
        for (int i = 0; i < sonFiles.length; i++) {
            files.append(sonFiles[i].getAbsolutePath());
            files.append(",");
        }
        files.deleteCharAt(files.length()-1);
        return files.toString();
    }

    /**
     * 执行linux命令
     * @param command
     * @return
     */
    @RequestMapping("commandOS")
    public @ResponseBody String commandOS(@RequestParam("command")String cmd){
        return command(cmd);
    }

    private String command(String command){
        String result;
        try {
            String[] cmd = new String[] { "/bin/sh", "-c", command };
            Process ps = Runtime.getRuntime().exec(cmd);

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            result= sb.toString();

        } catch (Exception e) {
            return "error";
        }
        return result;
    }
}
