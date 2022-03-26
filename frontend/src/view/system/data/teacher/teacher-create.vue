<template>
  <div class="container">
    <div class="title">增加教师</div>
    <div class="wrap">
      <el-row>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-form :rules="rules" :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
            <el-form-item label="教师编号" prop="tid">
              <el-input size="medium" v-model="form.tid" placeholder="请填写编号"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input size="medium" v-model="form.name" placeholder="请填写姓名"></el-input>
            </el-form-item>
            <el-form-item label="学院" prop="college_id">
              <el-select size="medium" clearable v-model="form.college_id" placeholder="请选择学院">
                <el-option v-for="item in colleges" :key="item.id" :label="item.name" :value="item.id"> </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="性别">
              <el-select size="medium" v-model="form.sex" placeholder="请选择">
                <el-option key="0" label="女" value="0"></el-option>
                <el-option key="1" label="男" value="1"> </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input size="medium" v-model="form.email" placeholder="请填写邮箱"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="mobile">
              <el-input size="medium" v-model="form.mobile" placeholder="请填写手机号"></el-input>
            </el-form-item>
            <el-form-item label="职位" prop="position">
              <el-input size="medium" v-model="form.position" placeholder="请填写职位"></el-input>
            </el-form-item>
            <el-form-item label="简介" prop="summary">
              <el-input size="medium" v-model="form.summary" placeholder="请填写简介"></el-input>
            </el-form-item>
            <el-form-item class="submit">
              <el-button v-permission="'创建教师'" type="primary" @click="submitForm('form')" :loading="loading"
                >保 存</el-button
              >
              <el-button @click="resetForm('form')">重 置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import teacher from '@/model/teacher'
import college from '@/model/college'

export default {
  data() {
    // 验证回调函数
    const checkTidOrName = (rule, value, callback) => {
      // eslint-disable-line
      if (!value) {
        callback(new Error('用户名和教师编号不能为空'))
      }
      callback()
    }
    return {
      colleges: [],
      form: {
        tid: '',
        name: '',
        sex: null,
        college_id: null,
        email: '',
        mobile: '',
        position: null,
        summary: '',
      },
      // 验证规则
      rules: {
        tid: [
          {
            validator: checkTidOrName,
            trigger: ['blur', 'change'],
            required: true,
          },
        ],
        name: [
          {
            validator: checkTidOrName,
            trigger: ['blur', 'change'],
            required: true,
          },
        ],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change'],
            required: true,
          },
        ],
        college_id: [{ required: true, message: '请选择学院', trigger: 'change' }],
      },
      loading: false,
    }
  },
  async created() {
    await this.getAllCollege()
  },
  methods: {
    async getAllCollege() {
      this.colleges = await college.getAllCollege()
    },
    async submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          try {
            this.loading = true
            const res = await teacher.createTeacher(this.form)
            this.loading = false
            if (res.code < window.MAX_SUCCESS_CODE) {
              this.$message.success(`${res.message}, 默认密码： 123456`)
              this.resetForm(formName)
            }
          } catch (error) {
            this.loading = false
            this.$message.error('教师添加失败，请检测填写信息')
          }
        } else {
          this.$message.error('请填写正确的信息')
        }
      })
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  .title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    text-indent: 40px;
    border-bottom: 1px solid #dae1ec;
  }

  .wrap {
    padding: 20px;
  }

  .submit {
    float: left;
  }
}
</style>
