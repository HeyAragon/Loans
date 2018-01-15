package com.hackhome.loans.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * desc:
 * author: aragon
 * date: 2017/12/29 0029.
 */
@Entity
public class ApplyRecordModel {

    @Id
    private Long id;
    private int state;
    private int taskId;
    private int btnPos;
    @Generated(hash = 1874900746)
    public ApplyRecordModel(Long id, int state, int taskId, int btnPos) {
        this.id = id;
        this.state = state;
        this.taskId = taskId;
        this.btnPos = btnPos;
    }
    @Generated(hash = 241263349)
    public ApplyRecordModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getTaskId() {
        return this.taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public int getBtnPos() {
        return this.btnPos;
    }
    public void setBtnPos(int btnPos) {
        this.btnPos = btnPos;
    }

}
