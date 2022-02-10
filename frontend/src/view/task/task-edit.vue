<template>
  <div>
    <div class="container">
        
          <el-divider></el-divider>
          <div class="title">
            <span>{{ isCreate ? '创建课题' : '更新课题' }}</span>
            <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
          </div>
          <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
            <el-form-item
              prop="name"
              :rules="[{ required: true, message: '请输入课题名称', trigger: 'blur' }]"
              label="课题名称"
            >
                <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item
              prop="description"
              :rules="[{ required: true, message: '请输入描述信息', trigger: 'blur' }]"
              label="描述信息"
            >
                <el-input v-model="form.description" autocomplete="off"></el-input>
            </el-form-item>

            
            <el-form-item
              prop="topic_type"
              :rules="[{ required: true, message: '请选择类别', trigger: 'blur' }]"
              label="类别"
            >
              <el-select size="medium" v-model="form.topic_type" placeholder="请选择类别">
                <el-option v-for="item in topicTypes" :key="item.id" :label="item.name" :value="item.id"> </el-option>
              </el-select>
            </el-form-item>
            
            <el-form-item
              prop="teacher_id"
              :rules="[{ required: true, message: '请选择老师', trigger: 'blur' }]"
              label="老师"
            >
              <el-select size="medium" v-model="form.teacher_id" placeholder="请选择老师">
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

            <el-form-item
              prop="graduate_info_id"
              label="年份"
              :rules="[{ required: true, message: '请选择年份', trigger: 'blur' }]"
            >
                <el-select size="medium" v-model="form.graduate_info_id" placeholder="请选择学年">
                <el-option v-for="item in graduateInfos" :key="item.id" :label="item.title" :value="item.id"> </el-option>
              </el-select>
            
            </el-form-item>

            <el-form-item class="submit">
              <el-button
                v-permission="{ permission: ['指导教师', '答辩小组'], type: 'disabled' }"
                type="primary"
                @click="submitForm('form')"
                >保 存</el-button
              >
              <el-button @click="resetForm('form')">重 置</el-button>
            </el-form-item>
          </el-form>

    </div>
  </div>
</template>

<script>
import teacher from '@/model/teacher'
import topic from '@/model/topic'
import graduateInfo from '@/model/graduateinfo'
import college from '@/model/college'

export default {
  components: {},
  props: {
    teacherId: {
        type: Number,
        default: null
    },
    graduateInfoId: {
        type: Number,
        default: null
    },
    isCreate: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      dialogFormVisible: false,
      loading: false,
      topicTypes: [],
      graduateInfos: [],
      teachers: [],
      colleges: [],
      form: {
        id: null,
        name: '',
        description: '',
        topic_type: null,
        teacher_id: null,
        college_id: null,
        graduate_info_id: null,
        status: 1,
      },
    }
  },
  async created() {
    this.initData()
  },
  methods: {
    back() {
      this.$emit('editClose')
    },

    submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          const postData = { ...this.form }
          let res
          if (this.isCreate) {
            res = await topic.createNewTopic(postData)
          } else {
            res = await team.updateExistingTopic(postData)
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

    async initData() {
        this.teachers = await teacher.getAllTeacher()
        this.colleges = await college.getAllCollege()
        this.topicTypes = await topic.allTopicTypes()
        this.graduateInfos = await graduateInfo.getGraduateInfoAll()
        this.form.graduate_info_id = this.graduateInfoId
        this.form.teacher_id = this.teacherId
        const currentCollege = await teacher.getCollegeByTeacherId(this.teacherId)
        if(currentCollege != null) {
            this.form.college_id = currentCollege.id
        }
        if(this.topicTypes.length > 0) {
            this.form.topic_type = this.topicTypes[0].id
        }
    },
  },
}
</script>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 0;
}

.pagination {
  display: flex;
  justify-content: flex-start;
  margin: 20px;
}

.container {
  padding: 20px;
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

  .wrap {
    padding: 20px;
    display: flex;
    justify-content: space-between;
  }

  .submit {
    float: left;
  }
}

.plus {
  display: flex;
  align-items: center;
}
</style>
