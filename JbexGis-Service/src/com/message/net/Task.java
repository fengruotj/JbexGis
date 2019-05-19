package com.message.net;
/** ����ӿ��� **/

import java.util.Date;

/**
* ��������ӿ�
* �����������̳з���
* 
* @author obullxl
*/
public abstract class Task implements Runnable {
    // private static Logger logger = Logger.getLogger(Task.class);
    /* ����ʱ�� */
    private Date generateTime = null;
    /* �ύִ��ʱ�� */
    private Date submitTime = null;
    /* ��ʼִ��ʱ�� */
    private Date beginExceuteTime = null;
    /* ִ�����ʱ�� */
    private Date finishTime = null;

    private long taskId;

    public Task() {
        this.generateTime = new Date();
    }

    /**
    * ����ִ�����
    */
    public void run() {
        /**
        * ���ִ�д���
        * 
        * beginTransaction();
        * 
        * ִ�й���п��ܲ����µ����� subtask = taskCore();
        * 
        * commitTransaction();
        * 
        * �����²�������� ThreadPool.getInstance().batchAddTask(taskCore());
        */
    }

    /**
    * ��������ĺ��� �����ر��ҵ���߼�ִ��֮��
    * 
    * @throws Exception
    */
    public abstract Task[] taskCore() throws Exception;

    /**
    * �Ƿ��õ���ݿ�
    * 
    * @return
    */
    protected abstract boolean useDb();

    /**
    * �Ƿ���Ҫ����ִ��
    * 
    * @return
    */
    protected abstract boolean needExecuteImmediate();

    /**
    * ������Ϣ
    * 
    * @return String
    */
    public abstract String info();

    public Date getGenerateTime() {
        return generateTime;
    }

    public Date getBeginExceuteTime() {
        return beginExceuteTime;
    }

    public void setBeginExceuteTime(Date beginExceuteTime) {
        this.beginExceuteTime = beginExceuteTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

}