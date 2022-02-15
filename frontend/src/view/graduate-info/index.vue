<template>
  <div class="container">
    <el-button :disabled="studentSId == ''" type="success" @click="handleDownload" round>下载我的评分表</el-button>
  </div>
</template>

<script>
import user from '@/lin/model/user'
import scorecard from '@/model/scorecard'
import graduateInfo from '@/model/graduateinfo'
import student from '@/model/student'

export default {
  data() {
    return {
      studentSId: '',
    }
  },
  async created() {
    this.checkLoginUser()
  },
  methods: {
    async handleDownload() {
      if (this.studentSId === '') {
        this.$message({
          type: 'error',
          message: '请登录学生账号',
        })
      } else {
        const res = await scorecard.getScorecardIdBySid(this.studentSId)
        if (res != null) {
          window.open(`${process.env.VUE_APP_BASE_URL}/v1/scorecard/download/${res}`, '_parent')
        }
      }
    },

    async checkLoginUser() {
      const result = await user.getInformation()
      this.loginUsername = result.username
      const re = new RegExp('\\d{10}')
      if (re.test(result.username)) {
        // Student
        this.studentSId = result.username
      }
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
