<template>
  <div class="container">
    <el-row :gutter="20" class="wrap">
      
      <div class="toolbox">
        <div class="title">
          <span>上传论文</span>
        </div>

        <div v-if="isStudent" class="toolbox-right" >
          <el-button size="medium" type="primary" @click="back">
            返 回
          </el-button>
        </div>
      </div>

      <!-- 表单 -->
      <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
        <el-form-item
          prop="essayTitle"
          :rules="[{ required: true, message: '请输入课题名称', trigger: 'blur' }]"
          label="论文标题"
        >
            <el-input v-model="form.essayTitle" autocomplete="off" :disabled="!isStudent"></el-input>
        </el-form-item>

        <el-form-item
          prop="studentNote"
          :rules="[{ required: true, message: '请输入描述信息', trigger: 'blur' }]"
          label="附注信息"
        >
            <el-input v-model="form.studentNote" autocomplete="off" :disabled="!isStudent"></el-input>
        </el-form-item>

        
        <el-form-item v-if="isStudent" label="选择文档"
          :rules="[{ required: true, message: '请输选择文档', trigger: 'blur' }]"
        >
          <input type="file" id="file" hidden @change="didSelectFile($event)" />
          
          <el-input placeholder="选择文档" disabled v-model="docPath" @keyup.enter.native="updateDocPath" class="input-with-select">
            <el-button slot="append" icon="el-icon-folder" type="success" @click="selectFile"></el-button>
          </el-input>
        </el-form-item>

        <el-form-item v-else
          prop="teacherNote"
          :rules="[{ required: true, message: '请输入描述信息', trigger: 'blur' }]"
          label="留言"
        >
            <el-input v-model="form.teacherNote" autocomplete="off"></el-input>
        </el-form-item>

        

        <el-form-item class="submit">
          <el-button
            type="primary"
            @click="submitForm('form')"
            >保 存</el-button
          >
          <el-button @click="resetForm('form')">重 置</el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </div>
</template>

<script>
import topic from '@/model/topic'
import user from '@/lin/model/user'
import graduateInfo from '@/model/graduateinfo'
import student from '@/model/student'
import teacher from '@/model/teacher'
import essay from '@/model/essay'
import stringUtils from '@/model/stringUtils'

export default {
  props: {
    essayId: {
      type: Number,
      default: null
    },
    isUpload: {
      type: Number,
      default: 0
    },
    topicAssignId: {
      type: Number,
      default: null
    },
    isCreate: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      graduateInfoId: 0,
      studentId: 0,
      teacherId: 0,
      isStudent: false,
      docPath: '',
      form: {
        topicAssignId: 0,
        essayTitle: '',
        studentNote: '',
        fileContent: '',
        fileExt: '',
        status: 0
      },

      loginUsername: '',
      loginUserType: '',
      essay: null,
      loading: false
    }
  },
  async created() {
    const res = await graduateInfo.getGraduateInfoAll()
    console.log("graduate info list: " + JSON.stringify(res))

    this.graduateInfoId = res[0].id

    await this.checkLoginUser()
    this._getData()
  },
  methods: {
    back() {
      this.$emit('editClose')
    },

    async _getData() {

      if(!this.isCreate && this.essayId != null) {
        let essayItem = await essay.getEssay(this.essayId)
        this.essay = essayItem

        this.form.topicAssignId = essayItem.topic_assign_id;
        this.form.essayTitle = essayItem.essay_title;
        this.form.studentNote = essayItem.student_note;
        }
    },

    handleSelect(data) {
      window.location = data.download_url
    },

    async checkLoginUser() {
      const result = await user.getInformation()
      this.loginUsername = result.username
      let re = new RegExp('\\d{10}')
      if (re.test(result.username)) {
        // Student
        this.loginUserType = 'student'
        const studentInfo = await student.getStudentByLoginName(result.username)
        this.studentId = studentInfo.id
        this.isStudent = true
        return
      }
      re = new RegExp('\\d{4}')
      if (re.test(result.username)) {
        // teacher
        this.loginUserType = 'teacher'

        const teacherInfo = await teacher.getTeacherByLognName(result.username)
        console.log("teacher info: " + JSON.stringify(teacherInfo))

        this.teacherId = teacherInfo.id

        return
      }

      this.$message.info('该界面root用户不给予展示!')
    },

    async didSelectFile(e) {
      // console.log('output file changed: ' + JSON.stringify(e))
      console.log('target: ' + e.target)
      try {
        var file = e.target.files[0];
        this.docPath = file.name
        if (!file) {
          return;
        }
        
        let buffer = await file.arrayBuffer()
        let str = stringUtils.arrayBufferToBase64(buffer)

        console.log("base64 string: " + str)
        console.log("file name： " + file.name)
        
        this.form.fileContent = str
        this.form.fileExt = stringUtils.getFileExt(file.name)
      } catch (error) {
        console.debug('choice file err:', error)
      }
    },

    async updateDocPath() {
      console.log("input document path: " + this.docPath)
      // const response = await fetch(this.docPath)
      // const text = await response.text()
      
      // console.log("read text: " + text)
      const file = new File(this.docPath)
      console.log("file: " + file)
      console.log("file name: " + file.name)
      fetch(this.docPath)
      .then(response => response.text())
      .then(data => {
        // Do something with your data
        console.log(data);
      });
    },

    selectFile(e) {

      console.log('select file changed: ' + JSON.stringify(e))
      var file2 = document.getElementById('file')
      console.log("get hidden input: " + file2)
      file2.click()
    },

    submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          let postData = { ...this.form }
          let res
          console.log("is uploading essay: " + this.isCreate)
          if (this.isCreate) {
            const data = {
              topicAssignId: this.topicAssignId,
              essayTitle: this.form.essayTitle,
              studentNote: this.form.studentNote,
              fileContent: this.form.fileContent,
              fileExt: this.form.fileExt,
              status: 0
            }
            res = await essay.uploadEssay(data)
          } else {
            postData.id = this.essay.id;
            res = await essay.updateExistingEssay(postData)
          }

          if (res != null) {
            this.$message.success(`${res.message}`)
            if (this.isCreate) {
              this.resetForm(formName)
            }
            this.$confirm('是否返回上一页?', '提示', {
              confirmButtonText: '是',
              cancelButtonText: '否',
              type: 'info',
            })
              .then(async () => {
                this.$emit('editClose')
              })
              .catch(async () => {
                if (!this.isCreate) {
                  await this.initData()
                }
              })
          }
        }
      })
    },

    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .cardTable {
    width: 100%;
    ol {
      list-style-type: decimal !important;

      li {
        list-style-type: decimal !important;
        font-size: 16px;

        ol {
          list-style-type: upper-alpha !important;
          li {
            font-size: 14px;
            list-style-type: upper-alpha !important;
            list-style-position: inside;
            text-indent: 2em;
          }
          li::marker {
            color: red;
          }
        }
      }
      li::marker {
        color: darkred;
      }
    }
  }
  .add-dialog {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

.title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    // text-indent: 40px;

    .back {
      float: right;
      margin-right: 40px;
      cursor: pointer;
    }
  }
  .pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px;
  }
}
</style>
