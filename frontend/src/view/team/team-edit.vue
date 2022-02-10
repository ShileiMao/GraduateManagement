<template>
  <div>
    <div class="container">
      <el-row :gutter="20" class="wrap">
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-divider></el-divider>
          <div class="title">
            <span>{{ isCreate ? '创建小组' : '更新小组' }}</span>
            <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
          </div>
          <el-form :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
            <el-form-item
              prop="name"
              :rules="[{ required: true, message: '请输入小组名称', trigger: 'blur' }]"
              label="小组名称"
            >
              <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item
              :rules="[{ required: true, message: '请输入届数', trigger: 'blur' }]"
              prop="years"
              label="届数"
            >
              <el-input v-model="form.years" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item prop="judges" label="评委团队名">
              <el-input v-model="form.judges" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item
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
              <el-select size="medium" v-model="form.college_id" placeholder="请选择学院" @change="searchAllStudents">
                <el-option v-for="item in colleges" :key="item.id" :label="item.name" :value="item.id"> </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              prop="student_ids"
              label="小组成员"
              :rules="[{ required: true, message: '请输入正确格式(学号 + , 隔开)', trigger: 'blur' }]"
            >
              <el-input v-model="form.student_ids" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item class="submit">
              <el-button
                v-permission="{ permission: ['创建团队', '更新团队'], type: 'disabled' }"
                type="primary"
                @click="submitForm('form')"
                >保 存</el-button
              >
              <el-button @click="resetForm('form')">重 置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-divider></el-divider>
          <div class="title plus">
            <span>团队成员</span>
            <!-- <el-button
              style="margin-left:30px;"
              @click.prevent="dialogFormVisible = true"
              type="primary"
              plain
              disabled
              size="medium"
              >添加成员</el-button
            > -->
          </div>
          <el-table stripe v-loading="loading" :data="studentItems">
            <el-table-column prop="sid" label="学号" width="150"></el-table-column>
            <el-table-column prop="name" label="姓名" width="150"></el-table-column>
            <el-table-column width="150" fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button @click.prevent="handleDeleteStudentItem(scope.row)" type="danger" size="mini" plain
                  >移除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>

    <el-dialog title="添加团队成员" :visible.sync="dialogFormVisible">

      <el-form :model="form2">
        <el-select v-model="pendingStudents" @change="handleAddTeamStudent">
          <el-option v-for="item in students" :key="item.id" :label="item.name" :value="item.id"> </el-option>
        </el-select>
        
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddTeamStudent">确 定</el-button>
        </div>
      </el-form>
      <!-- <select v-model="pendingStudents" :items="students" item-value="id" item-text="name" label="name"/> -->
      
    </el-dialog>
  </div>
</template>

<script>
import team from '@/model/team'
import teacher from '@/model/teacher'
import college from '@/model/college'

export default {
  components: {},
  props: {
    isCreate: {
      type: Boolean,
      default: false,
    },
    teamId: {
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
        years: null,
        college_id: null,
        teacher_id: null,
        student_ids: null,
      },
      form2: {

      },
      teachers: [],
      colleges: [],
      students: [],
      pendingStudents: '',
    }
  },
  async created() {
    console.log("did create ");
    this.$nextTick(async () => {
      if (!this.isCreate) {
        await this.initTeam()
        this.initStudentItems()
      }
    })
    this.initData()
  },
  methods: {
    async searchAllStudents(value) {
      console.log('did select college: ' + value)
      this.students = await team.getAllStudents(value)
      console.log('all students: ' + JSON.stringify(this.students))
    },
    handleAddTeamStudent(item) {
      console.log('student ids: ' + this.form.student_ids)
      if (this.form.student_ids === '') {
        this.form.student_ids = item.id
      } else {
        this.form.student_ids += "," + item.id
      }
      this.initStudentItems()
    },
    handleDeleteStudentItem(data) {
      this.$confirm('此操作将移除该成员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await team.deleteTeamStudents(data.sid, this.form.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          await this.initTeam()
          this.initStudentItems()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        }
      })
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
            res = await team.createTeam(postData)
          } else {
            res = await team.updateTeam(this.teamId, postData)
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
                  await this.initTeam()
                  this.initStudentItems()
                }
              })
          }
        }
      })
    },

    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    async initStudentItems() {
      console.log('init student items')
      console.log(this.form.student_ids)
      if (this.form.student_ids == null || this.form.student_ids === '') {
        this.studentItems = []
        return
      }
      this.loading = true
      const res = await team.getTeamStudents(this.form.student_ids)
      this.studentItems = res
      this.loading = false
    },

    async initTeam() {
      const res = await team.getTeam(this.teamId)
      this.form = res
    },

    async initData() {
      if (!window.sessionStorage.getItem('teachers')) {
        const res = await teacher.getAllTeacher()
        this.teachers = res
        window.sessionStorage.setItem('teachers', JSON.stringify(res))
      } else {
        this.teachers = JSON.parse(window.sessionStorage.getItem('teachers'))
      }

      const res = await teacher.getAllTeacher()
      this.teachers = res
      // this.colleges = JSON.parse(window.sessionStorage.getItem('colleges'))
      this.colleges = await college.getAllCollege()
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
