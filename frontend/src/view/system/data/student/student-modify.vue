<template>
  <div class="container">
    <div class="title">
      <span>修改学生</span> <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
    </div>
    <el-divider></el-divider>
    <div class="wrap">
      <el-row>
        <el-col :lg="16" :md="20" :sm="24" :xs="24">
          <el-form :model="form" status-icon ref="form" label-width="100px" v-loading="loading" @submit.native.prevent>
            <el-form-item label="姓名" prop="name">
              <el-input size="medium" v-model="form.name" placeholder="请填写姓名"></el-input>
            </el-form-item>
            <el-form-item label="班级" prop="class_name">
              <el-tag>{{ form.class_name }}</el-tag>
            </el-form-item>
            <el-form-item label="性别">
              <el-select size="medium" v-model="form.sex" placeholder="请选择">
                <el-option key="0" label="女" value="0"></el-option>
                <el-option key="1" label="男" value="1"> </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="手机号" prop="mobile">
              <el-input size="medium" v-model="form.mobile" placeholder="请填写手机号"></el-input>
            </el-form-item>
            <el-form-item class="submit">
              <el-button type="primary" @click="submitForm('form')">保 存</el-button>
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

export default {
  props: {
    editStudentID: {
      type: Number,
    },
    classId: {
      type: Number,
    },
  },
  data() {
    return {
      loading: false,
      form: {
        name: '',
        sex: null,
        major: '',
        mobile: '',
      },
    }
  },
  async mounted() {
    this.loading = true
    this.form = await student.getStudent(this.editStudentID)
    this.form.sex = this.form.sex != null ? this.form.sex.toString() : null
    this.loading = false
  },
  methods: {
    async submitForm() {
      this.form.class_id = this.classId
      const res = await student.editStudent(this.editStudentID, this.form)
      if (res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success(`${res.message}`)
        this.$emit('editClose')
      }
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    back() {
      this.$emit('editClose')
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
</style>
