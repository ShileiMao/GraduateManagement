<template>
  <div>
    <div v-if="!showEdit" class="container">
      <div class="header">
        <p class="title">团队列表</p>
      </div>
      <div class="toolbox">
        <div class="toolbox-left">
          <el-button style="margin-left:30px;" @click.prevent="addTeam" type="primary" plain size="medium"
            >添加团队</el-button
          >
        </div>
        <div class="toolbox-right">
          <lin-search @query="onQueryChange" placeholder="请输入团队名称" />
        </div>
      </div>

      <!-- 表格 -->
      <el-table v-loading="loading" :data="tableData" stripe>
        <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" width="120"></el-table-column>
        <el-table-column prop="teacher_id" label="负责人ID" width="80"></el-table-column>
        <el-table-column prop="judges" label="评委团队名" width="120"></el-table-column>
        <el-table-column prop="college_id" :formatter="collegeFormatter" label="所属学院" width="120"></el-table-column>
        <el-table-column prop="years" label="届数" width="50"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row)" type="primary" plain size="mini">编辑</el-button>
            <el-button @click="handleDelete(scope.row)" type="danger" size="mini" plain>删除</el-button>
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

    <team-edit v-else @editClose="editClose" :teamId="teamId" :isCreate="isCreate"> </team-edit>
  </div>
</template>

<script>
import LinSearch from '@/component/base/search/lin-search'
import team from '@/model/team'
import college from '@/model/college'
import TeamEdit from './team-edit'

export default {
  components: {
    LinSearch,
    TeamEdit,
  },
  data() {
    return {
      tableData: [],
      loading: false,
      isCreate: false,
      teamId: null,
      showEdit: false,
      // 分页相关
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
      currentPage: 1, // 默认获取第一页的数据
      pageCount: 10, // 每页10条数据
      totalNums: 180, // 分组内的用户总数
      searchKeyword: '',
      colleges: [],
    }
  },
  async created() {
    this.loading = true
    this.getAllCollege()
    this._getTableData((this.currentPage - 1) * this.pageCount, this.pageCount)
    this.loading = false
  },
  methods: {
    async _getTableData() {
      const currentPage = this.currentPage - 1
      const res = await team.getTeamByPage(null, currentPage, this.pageCount)
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

    async getAllCollege() {
      if (!window.sessionStorage.getItem('colleges')) {
        const res = await college.getAllCollege()
        this.colleges = res
        window.sessionStorage.setItem('colleges', JSON.stringify(res))
      } else {
        this.colleges = JSON.parse(window.sessionStorage.getItem('colleges'))
      }
    },

    collegeFormatter(row, column) {
      let result = '学院不存在'
      for (const college of this.colleges) {
        if (row.college_id == college.id) {
          result = college.name
          break
        }
      }
      return result
    },

    // 搜索
    async onQueryChange(query) {
      this.searchKeyword = query.trim()
      if (!query) {
        await this._getTableData()
        return
      }
      this.loading = true
      setTimeout(async () => {
        this.loading = false
        const res = await team.getTeamByPage(this.searchKeyword, this.currentPage - 1, this.pageCount)
        this.tableData = res.items
      }, 1000)
    },

    addTeam() {
      this.isCreate = true
      this.teamId = null
      this.showEdit = true
    },

    editClose() {
      this.showEdit = false
      this._getTableData()
    },

    handleEdit(val) {
      this.isCreate = false
      this.teamId = `${val.id}`
      this.showEdit = true
    },

    handleDelete(val) {
      this.$confirm('此操作将永久删除该团队, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await team.deleteTeam(val.id)
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
