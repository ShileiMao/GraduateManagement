<template>
  <div>
    <sticky-top>
      <div class="title">
        <span>{{ isCreate ? '创建课题' : '更新课题' }}</span>
        <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
      </div>
    </sticky-top>
    <div class="container">
      <div class="wrap">
        <el-row>
          <el-col :lg="16" :md="20" :sm="24" :xs="24">
            <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
              <el-form-item
                prop="name"
                :rules="[{ required: true, message: '请输入课题名称', trigger: 'blur' }]"
                label="课题名称"
              >
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item
                :rules="[{ required: true, message: '请输入描述', trigger: 'blur' }]"
                prop="description"
                label="描述"
              >
                <el-input v-model="form.description" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item
                :rules="[{ required: true, message: '请输入学生ID', trigger: 'blur' }]"
                prop="student_id"
                label="学生ID"
              >
                <el-input v-model="form.student_id" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item
                :rules="[{ required: true, message: '请输入发布年份', trigger: 'blur' }]"
                prop="publish_year"
                label="发布年份"
              >
                <el-input v-model="form.publish_year" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item
                prop="type"
                :rules="[{ required: true, message: '请选择类型', trigger: 'blur' }]"
                label="类型"
              >
                <el-select size="medium" v-model="form.type" placeholder="请选择类型">
                  <el-option
                    v-if="loginUserType == 'teacher' || loginUserType == 'root'"
                    key="0"
                    label="老师发布"
                    value="0"
                  >
                  </el-option>
                  <el-option
                    v-if="loginUserType == 'student' || loginUserType == 'root'"
                    key="1"
                    label="学生申请"
                    value="1"
                  >
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item
                prop="teacher_id"
                :rules="[{ required: true, message: '请选择所属论文答辩', trigger: 'blur' }]"
                label="论文答辩"
              >
                <el-select
                  @change="getGuideTeacher"
                  size="medium"
                  v-model="form.graduate_info_id"
                  placeholder="请选择所属论文答辩"
                >
                  <el-option v-for="item in graduateinfos" :key="item.id" :label="item.title" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                v-if="teachers"
                prop="teacher_id"
                :rules="[{ required: true, message: '请选择负责人', trigger: 'blur' }]"
                label="负责人"
              >
                <el-select size="medium" v-model="form.teacher_id" placeholder="请选择负责人">
                  <el-option v-for="item in teachers" :key="item.id" :label="item.name" :value="item.id"> </el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                prop="college_id"
                :rules="[{ required: true, message: '请选择学院', trigger: 'blur' }]"
                label="所属学院"
              >
                <el-select size="medium" v-model="form.college_id" placeholder="请选择学院">
                  <el-option v-for="item in colleges" :key="item.id" :label="item.name" :value="item.id"> </el-option>
                </el-select>
              </el-form-item>
              <el-form-item class="submit">
                <el-button
                  v-permission="{ permission: ['创建课题', '更新课题'] }"
                  type="primary"
                  @click="submitForm('form')"
                  >保 存</el-button
                >
                <el-button @click="resetForm('form')">重 置</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import topic from '@/model/topic'
import college from '@/model/college'
import user from '@/lin/model/user'
import graduateinfo from '@/model/graduateinfo'

export default {
  props: {
    isCreate: {
      type: Boolean,
      default: false,
    },
    topicId: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      dialogFormVisible: false,
      loading: false,
      studentItems: [],
      form: {
        id: null,
        name: '',
        publish_year: null,
        college_id: null,
        teacher_id: null,
        student_id: null,
        description: null,
        type: null,
        graduate_info_id: null,
      },
      teachers: [],
      colleges: [],
      graduateinfos: [],
      loginUserType: '',
    }
  },
  async created() {
    this.$nextTick(async () => {
      if (!this.isCreate) {
        await this.initTopic()
        this.getGuideTeacher()
      }
    })

    this.checkLoginUser()

    this.initData()
  },
  methods: {
    async checkLoginUser() {
      const result = await user.getInformation()
      let re = new RegExp('\\d{10}')
      if (re.test(result.username)) {
        // Student
        this.loginUserType = 'student'
        return
      }
      re = new RegExp('\\d{4}')
      if (re.test(result.username)) {
        // teacher
        this.loginUserType = 'teacher'
        return
      }
      if (result.username === 'root') {
        this.loginUserType = 'root'
      }
    },

    back() {
      this.$emit('editClose')
    },

    submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          const postData = { ...this.form }
          let res
          if (this.isCreate) {
            res = await topic.createTopic(postData)
          } else {
            res = await topic.updateTopic(this.topicId, postData)
          }
          if (res.code < window.MAX_SUCCESS_CODE) {
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
                  await this.initTopic()
                }
              })
          }
        }
      })
    },

    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    async getGuideTeacher() {
      const res = await graduateinfo.getGuideTeachersByGraduateId(this.form.graduate_info_id)
      this.teachers = res
    },

    async initTopic() {
      const res = await topic.getTopic(this.topicId)
      this.form = res
      this.form.type = `${res.type}`
    },

    async initData() {
      let res = await graduateinfo.getGraduateInfoAll()
      this.graduateinfos = res
      if (!window.sessionStorage.getItem('colleges')) {
        res = await college.getAllCollege()
        this.colleges = res
        window.sessionStorage.setItem('colleges', JSON.stringify(res))
      } else {
        this.colleges = JSON.parse(window.sessionStorage.getItem('colleges'))
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 0;
}

.container {
  .title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    text-indent: 40px;

    .back {
      float: right;
      margin-right: 40px;
      cursor: pointer;
    }
  }

  .wrap {
    padding: 20px;
  }

  .submit {
    float: left;
  }
}

.display_img {
  max-width: 100px;
  max-height: 50px;
  width: auto;
  height: auto;
}

.plus {
  display: flex;
  justify-content: space-between;
}

.complete {
  margin-top: 20px;
  display: flex;
  .add {
    margin-left: 10px;
  }
}

.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;
    display: inline-flex;
    align-content: space-around;
    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .id {
      margin-right: 15px;
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
</style>
