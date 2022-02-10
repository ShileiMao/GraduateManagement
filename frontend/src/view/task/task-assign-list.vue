<template>
  <div class="container">
    <el-row :gutter="20" class="wrap">
      <el-col :lg="16" :md="20" :sm="24" :xs="24">
      <el-divider></el-divider>

        <div class="title">
          <span>选题情况</span>
          <span class="back" @click="back"> <i class="iconfont icon-fanhui"></i> 返回 </span>
        </div>
      </el-col>

      <!-- 表格 -->
      <el-table v-loading="loading" :data="tableData" stripe>
        <el-table-column prop="name" label="名称" width="200"></el-table-column>
        <el-table-column prop="description" label="描述信息" width="300"></el-table-column>
        <el-table-column prop="topic_type_name" label="课题类别" width="100"></el-table-column>
        <el-table-column prop="teacher_name" label="负责老师" width="100"></el-table-column>

        <el-table-column label="操作" width="400">
          <template slot-scope="scope">
            <el-button @click="handleSelect(scope.row)" type="primary" plain size="mini">
              选 择
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>

  </div>
</template>

<script>
import topic from '@/model/topic'
import user from '@/lin/model/user'
import cardApi from '@/model/card'
import scorecardApi from '@/model/scorecard'

export default {
  props: {
    teacherId: {
        type: Number,
        default: null
    },
    graduateInfoId: {
        type: Number,
        default: null
    },
    showingTask: {
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

      loginUsername: '',
      loginUserType: '',
      tableData: [],
      loading: false
    }
  },
  async created() {
    await this.checkLoginUser()
    this._getTableData()
  },
  methods: {
    back() {
      this.$emit('editClose')
    },

    async _getTableData() {
      let _topics = null
      
      _topics = await topic.getAssignByTaskId(this.graduateInfoId, this.showingTask)


      this.tableData = _topics
    },

    async checkLoginUser() {
      const result = await user.getInformation()
      this.loginUsername = result.username
      let re = new RegExp('\\d{10}')
      if (re.test(result.username)) {
        // Student
        this.loginUserType = 'student'

        this.$message.info('该界面学生用户不给予展示!')
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
