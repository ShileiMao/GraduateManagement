<template>
  <div class="container">
    <div class="title">增加学生</div>
    <div class="wrap">
      <el-row>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-form :rules="rules" :model="form" status-icon ref="form" label-width="100px" @submit.native.prevent>
            <el-form-item label="学号" prop="sid">
              <el-input size="medium" v-model="form.sid" placeholder="请填写学号"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input size="medium" v-model="form.name" placeholder="请填写姓名"></el-input>
            </el-form-item>
            <el-form-item label="班级" prop="class_id">
              <el-select size="medium" clearable v-model="form.class_id" placeholder="请选择班级">
                <el-option v-for="item in sClass" :key="item.id" :label="item.name" :value="item.id"> </el-option>
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
            <el-form-item class="submit">
              <el-button v-permission="'创建学生'" type="primary" @click="submitForm('form')" :loading="loading"
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
import student from '@/model/student'
import classApi from '@/model/class'

export default {
  data() {
    // 验证回调函数
    const checkSidOrName = (rule, value, callback) => {
      // eslint-disable-line
      if (!value) {
        callback(new Error('用户名和学生不能为空'))
      }
      callback()
    }
    return {
      sClass: [],
      form: {
        sid: '',
        name: '',
        sex: null,
        class_id: null,
        email: '',
        mobile: '',
      },
      // 验证规则
      rules: {
        sid: [
          {
            validator: checkSidOrName,
            trigger: ['blur', 'change'],
            required: true,
          },
        ],
        name: [
          {
            validator: checkSidOrName,
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
        class_id: [{ required: true, message: '请选择班级', trigger: 'change' }],
      },
      loading: false,
    }
  },
  async created() {
    await this.getAllClass()
  },
  methods: {
    async getAllClass() {
      this.sClass = await classApi.getAllClass()
    },
    async submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          try {
            this.loading = true
            const res = await student.createStudent(this.form)
            this.loading = false
            if (res.code < window.MAX_SUCCESS_CODE) {
              this.$message.success(`${res.message}`)
              this.resetForm(formName)
            }
          } catch (error) {
            this.loading = false
            this.$message.error('学生添加失败，请检测填写信息')
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
