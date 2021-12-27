<template>
  <div>
    <div v-if="!showEdit" class="container">
      <div class="header">
        <p class="title">课题任务</p>
      </div>
      <div class="toolbox">
        <div class="toolbox-left">
          <el-button style="margin-left:30px;" @click.prevent="addTopic" type="primary" plain size="medium"
            >添加课题</el-button
          >
        </div>
        <div class="toolbox-right">
          <lin-search @query="onQueryChange" placeholder="请输入课题名称" />
        </div>
      </div>
      <!-- 表格 -->
      <el-table v-loading="loading" :data="tableData" stripe>
        <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" width="200"></el-table-column>
        <el-table-column prop="teacher_name" label="负责人" width="120"></el-table-column>
        <el-table-column prop="student_name" label="选课人" width="120">
          <template slot-scope="scope">
            {{ scope.row.student_id != '' ? scope.row.student_name : '暂无' }}
          </template>
        </el-table-column>
        <el-table-column prop="college_name" label="所属学院" width="120"></el-table-column>
        <el-table-column prop="publish_year" label="发布年份" width="100"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag type="success">
              {{ status[scope.row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
              v-permission="{ permission: ['更新课题'], type: 'disabled' }"
              @click="handleEdit(scope.row)"
              type="primary"
              plain
              size="mini"
              >编辑</el-button
            >
            <el-button
              v-permission="{ permission: ['删除课题'], type: 'disabled' }"
              @click="handleDelete(scope.row)"
              type="danger"
              size="mini"
              plain
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <div v-if="!searchKeyword" class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          :background="true"
          :page-size="pageCount"
          :current-page="currentPage"
          v-if="refreshPagination"
          layout="prev, pager, next, jumper"
          :total="totalNums"
        ></el-pagination>
      </div>
    </div>
    <topic-edit v-else @editClose="editClose" :topicId="topicId" :isCreate="isCreate"> </topic-edit>
  </div>
</template>

<script>
import LinSearch from '@/component/base/search/lin-search'
import topic from '@/model/topic'
import college from '@/model/college'
import TopicEdit from './topic-edit'

export default {
  components: {
    LinSearch,
    TopicEdit,
  },
  data() {
    return {
      tagColor: [],
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
      tableData: [],
      loading: false,
      isCreate: false,
      showEdit: false,
      topicId: null,
      // 分页相关
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
      currentPage: 1, // 默认获取第一页的数据
      pageCount: 10, // 每页10条数据
      totalNums: 180, // 分组内的用户总数
      searchKeyword: '',
    }
  },
  created() {
    this.loading = true
    this._getTableData((this.currentPage - 1) * this.pageCount, this.pageCount)
    this.loading = false
  },
  methods: {
    async _getTableData() {
      const currentPage = this.currentPage - 1
      const res = await topic.getTopicByPage(null, currentPage, this.pageCount)
      this.tableData = res.items
      this.totalNums = res.total
    },
    // 切换分页
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true
      await this._getTableData()
      this.loading = false
    },
    addTopic() {
      this.isCreate = true
      this.topicId = null
      this.showEdit = true
    },
    editClose() {
      this.showEdit = false
      this._getTableData()
    },

    handleEdit(val) {
      this.isCreate = false
      this.topicId = `${val.id}`
      this.showEdit = true
    },
    // 搜索
    onQueryChange(query) {
      this.searchKeyword = query.trim()
      if (!query) {
        this._getTableData()
        return
      }
      this.loading = true
      setTimeout(async () => {
        this.loading = false
        const res = await topic.getTopicByPage(this.searchKeyword, this.currentPage - 1, this.pageCount)
        this.tableData = res.items
      }, 1000)
    },
    handleDelete(data) {
      this.$confirm('此操作将永久删除该课题, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await topic.deleteTopic(data.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          if (this.totalNums % this.pageCount === 1 && this.currentPage !== 1) {
            // 判断删除的是不是每一页的最后一条数据
            this.currentPage--
          }
          await this._getTableData()
          this.$message({
            type: 'success',
            // message: `${res.message}`,
            message: '删除成功',
          })
        } else {
          this.$message({
            type: 'error',
            message: `${res.message}`,
          })
        }
      })
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

  .toolbox {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    border-bottom: 1px solid #dae1ed;
    padding-bottom: 10px;

    .toolbox-left {
      float: left;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .toolbox-right {
      float: right;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }

  .pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px;
  }
}
</style>
