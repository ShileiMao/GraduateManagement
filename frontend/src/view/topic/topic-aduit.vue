<template>
  <div class="container">
    <div class="header">
      <p class="title">课题审核</p>
    </div>
    <!-- 表格 -->
    <el-table v-loading="loading" :data="tableData" stripe>
      <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
      <el-table-column prop="name" label="名称" width="200"></el-table-column>
      <el-table-column prop="publish_year" label="发布年份" width="100"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag type="success">
            {{ status[scope.row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button
            @click="handleSubmit(scope.row, initButtonText(scope.row)[0])"
            v-permission="{ permission: ['审核课题'], type: 'disabled' }"
            type="primary"
            plain
            size="mini"
            >{{ initButtonText(scope.row)[0] }}</el-button
          >
          <el-button
            @click="handleSubmit(scope.row, initButtonText(scope.row)[1])"
            v-permission="{ permission: ['审核课题'], type: 'disabled' }"
            type="primary"
            plain
            size="mini"
            >{{ initButtonText(scope.row)[1] }}</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import topic from '@/model/topic'
import user from '@/lin/model/user'

export default {
  data() {
    return {
      loginUsername: '',
      loginUserType: '',
      status: {
        0: '开始',
        1: '申请课题',
        2: '申请通过',
        3: '申请未通过',
        4: '课题被选',
        5: '课题未被选',
        6: '一审通过',
        7: '一审未通过',
        8: '二审通过',
        9: '二审未通过',
        10: '三审通过',
        11: '三审未通过',
        100: '结束',
      },
      texts: {
        0: ['申请通过', '申请未通过'],
        1: ['一审通过', '一审未通过'],
        2: ['二审通过', '二审未通过'],
        3: ['三审通过', '三审未通过'],
      },
      tableData: [],
      loading: false,
    }
  },
  async created() {
    await this.checkLoginUser()
    this._getTableData()
  },
  methods: {
    async _getTableData() {
      if (this.loginUserType == 'teacher') {
        const res = await topic.getTopicsByTeacherId(this.loginUsername)
        this.tableData = res
      } else if (this.loginUserType == 'student') {
        const res = await topic.getTopicByStudentSid(this.loginUsername)
        this.tableData = [res]
      }
    },
    async handleSubmit(data, statusName) {
      if (statusName == '无操作') return
      await topic.updateStatus({ id: data.id, status_name: statusName })
      this._getTableData()
    },
    // 切换分页
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true
      await this._getTableData()
      this.loading = false
    },

    initButtonText(data) {
      if (data.status < 2) {
        return this.texts[0]
      } if (data.status < 6) {
        return this.texts[1]
      } if (data.status < 8) {
        return this.texts[2]
      } if (data.status < 10) {
        return this.texts[3]
      }
      return ['无操作', '无操作']
    },

    async checkLoginUser() {
      const result = await user.getInformation()
      this.loginUsername = result.username
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
      this.$message.info('该界面root用户不给予展示!')
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .header {
    display: flex;
    justify-content: flex-start;
    align-items: center;

    .title {
      height: 59px;
      line-height: 59px;
      color: $parent-title-color;
      font-size: 16px;
      font-weight: 500;
    }
  }

  .pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px;
  }
}
</style>
