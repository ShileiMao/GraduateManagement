<template>
  <div class="container">
    <el-button type="success" @click="dialogVisible = true" round>开启论文</el-button>
    <!-- <el-button type="info" round>告知此次答辩老师信息</el-button> -->

    <el-dialog title="设置信息" :visible.sync="dialogVisible" width="30%">
      <el-input v-model="form.title" placeholder="请输入标题"></el-input>
      <el-date-picker v-model="form.year" value-format="yyyy" type="year" placeholder="选择年" style="margin-top:10px;">
      </el-date-picker>
      <el-select size="medium" multiple v-model="form.teacher_ids" placeholder="请选择负责人" style="margin-top:10px;">
        <el-option v-for="item in teachers" :key="item.id" :label="item.name" :value="item.id"> </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import teacher from '@/model/teacher'
import graduateinfo from '@/model/graduateinfo'

export default {
  data() {
    return {
      dialogVisible: false,
      teachers: [],
      form: {
        title: '',
        teacher_ids: null,
        year: '',
      },
    }
  },
  async created() {
    this.initData()
  },
  methods: {
    async handleClick() {
      if (this.form.title === '' || this.year === '' || this.form.teacher_ids == null) {
        this.$message.error('请输入所有信息')
        return
      }
      // if (this.form.teacher_ids.length !== 5) {
      //   this.$message.error('请选择5个指导教师')
      //   return
      // }
      const res = await graduateinfo.createGraduateInfo(this.form)
      if (res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success(`${res.message}`)
        this.$message.success('请为指导教师分配指导教师权限')
        this.dialogVisible = false
        this.restData()
      }
    },

    restData() {
      this.form = {
        title: '',
        teacher_id: null,
        year: '',
      }
    },

    async initData() {
      // if (!window.sessionStorage.getItem('teachers')) {
      //   const res = await teacher.getAllTeacher()
      //   this.teachers = res
      //   window.sessionStorage.setItem('teachers', JSON.stringify(res))
      // } else {
      //   this.teachers = JSON.parse(window.sessionStorage.getItem('teachers'))
      // }
      const res = await teacher.getAllTeacher()
      this.teachers = res
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;
  margin-top: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
