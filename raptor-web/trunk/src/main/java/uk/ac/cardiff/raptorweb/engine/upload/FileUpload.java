package uk.ac.cardiff.raptorweb.engine.upload;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;



public class FileUpload implements Serializable{

    /** Generated Serial ID */
    private static final long serialVersionUID = 931048017059374672L;

    /** Class logger*/
    private final Logger log = LoggerFactory.getLogger(FileUpload.class);

    private ArrayList<LogFileUpload> files = new ArrayList<LogFileUpload>();
    private int uploadsAvailable;
    private int MAX_UPLOADS = 2;
    private boolean autoUpload = false;
    private boolean useFlash = false;

    private String processingStatus;


    public FileUpload(){
        uploadsAvailable = MAX_UPLOADS;
        processingStatus="";
    }


    public int getSize() {
        if (getFiles().size()>0){
            return getFiles().size();
        }else
        {
            return 0;
        }
    }


    public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer)object).getData());

    }

    public void listener(UploadEvent event) throws Exception{
        log.info("File upload event started, with event {}",event);
        UploadItem item = event.getUploadItem();
        long transactionId = (long) (Math.random()*1000000000);
        LogFileUpload file = new LogFileUpload();
        file.setLength(item.getData().length);
        file.setName(item.getFileName());
        file.setData(item.getData());
        file.setId(transactionId);
        file.setProcessingStatus("Ready to upload");
        files.add(file);
        log.info("Upload item is {}, with data length {}. FileUpload now has {} files, with {} upload available",new Object[]{item.getContentType(),item.getData().length,files.size(),uploadsAvailable});
        uploadsAvailable--;
    }

    public String clearUploadData() {
        ArrayList<LogFileUpload> toRemove = new ArrayList<LogFileUpload>();

        for (LogFileUpload lfu : files){
            if (lfu.isProcessed()){
                toRemove.add(lfu);
                uploadsAvailable++;
            }
        }
        files.removeAll(toRemove);

        return null;
    }

    public void clearAllData(){
        files.clear();
        setUploadsAvailable(MAX_UPLOADS);
    }


    public long getTimeStamp(){
        return System.currentTimeMillis();
    }

    public ArrayList<LogFileUpload> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<LogFileUpload> files) {
        this.files = files;
    }

    public int getUploadsAvailable() {
        return uploadsAvailable;
    }

    public void setUploadsAvailable(int uploadsAvailable) {
        this.uploadsAvailable = uploadsAvailable;
    }

    public boolean isAutoUpload() {
        return autoUpload;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }

    public boolean isUseFlash() {
        return useFlash;
    }

    public void setUseFlash(boolean useFlash) {
        this.useFlash = useFlash;
    }


    public void processingStatus(String status) {
        this.processingStatus = status;

    }

    public String getProcessingStatus(){
        return processingStatus;
    }

}
