<template>
  <div>
    <!-- 列表页面 -->
    <div class="container" v-if="!showEdit">
      <div class="header"><div class="title">学生列表</div></div>
      <div class="toolbox">
        <el-input
          @change="handleSearch"
          placeholder="请输入学生姓名进行搜索，可以直接回车搜索..."
          prefix-icon="el-icon-search"
          clearable
          style="width: 350px;margin-right: 10px"
          v-model="searchKeyword"
        ></el-input>
        <!-- <el-upload
          :show-file-list="false"
          :before-upload="beforeUpload"
          :on-success="onSuccess"
          :on-error="onError"
          :disabled="importDataDisabled"
          style="display: inline-flex;margin-right: 8px"
          action="/employee/basic/import"
        >
          <el-button :disabled="importDataDisabled" type="primary" :icon="importDataBtnIcon">
            {{ importDataBtnText }}
          </el-button>
        </el-upload> -->
      </div>
      <!-- 表格 -->
      <lin-table
        :tableColumn="tableColumn"
        :tableData="tableData"
        :operate="operate"
        @handleEdit="handleEdit"
        @handleDelete="handleDelete"
        @row-click="rowClick"
        v-loading="loading"
      ></lin-table>
      <!-- 分页 -->
      <div class="pagination" v-if="!searchKeyword">
        <el-pagination
          @current-change="handleCurrentChange"
          :background="true"
          :page-size="pageCount"
          :current-page="currentPage"
          layout="prev, pager, next, jumper"
          v-if="refreshPagination"
          :total="total_nums"
        ></el-pagination>
      </div>
    </div>

    <!-- 编辑页面 -->
    <student-modify v-else @editClose="editClose" :editStudentID="editStudentID" :classId="classId"></student-modify>
  </div>
</template>

<script>
import student from '@/model/student'
import LinTable from '@/component/base/table/lin-table'
import StudentModify from './student-modify'

export default {
  components: {
    LinTable,
    StudentModify,
  },
  data() {
    return {
      tableColumn: [
        { prop: 'id', label: 'id' },
        { prop: 'sid', label: '学号' },
        { prop: 'name', label: '姓名' },
        {
          prop: 'sex',
          label: '性别',
          formatter: (row, column, cellValue, index) => (cellValue === 1 ? '男' : cellValue === 0 ? '女' : '未知'),
        },
        { prop: 'class_name', label: '班级' },
        { prop: 'major_name', label: '专业' },
        { prop: 'college_name', label: '学院' },
        { prop: 'mobile', label: '手机号' },
        { prop: 'email', label: '邮箱' },
      ],
      loading: false,
      tableData: [],
      operate: [],
      showEdit: false,
      editStudentID: 1,
      classId: 1,
      searchKeyword: null,
      importDataBtnText: '导入数据',
      importDataDisabled: false,
      importDataBtnIcon: 'el-icon-upload2',
      // 分页相关
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
      currentPage: 1, // 默认获取第一页的数据
      pageCount: 10, // 每页10条数据
      total_nums: 180, // 分组内的用户总数
    }
  },
  async created() {
    this.loading = true
    await this._getTableData()
    this.operate = [
      { name: '编辑', func: 'handleEdit', type: 'primary', permission: '更新学生' },
      {
        name: '删除',
        func: 'handleDelete',
        type: 'danger',
        permission: '删除学生',
      },
    ]
    this.loading = false
  },
  methods: {
    // 文件导入
    beforeUpload() {
      this.importDataBtnText = '正在导入'
      this.importDataBtnIcon = 'el-icon-loading'
      this.importDataDisabled = true
    },
    onSuccess(response, file, fileList) {
      this.importDataBtnText = '导入数据'
      this.importDataBtnIcon = 'el-icon-upload2'
      this.importDataDisabled = false
      this._getTableData()
    },
    onError(err, file, fileList) {
      this.importDataBtnText = '导入数据'
      this.importDataBtnIcon = 'el-icon-upload2'
      this.importDataDisabled = false
    },

    async handleSearch() {
      if (!this.searchKeyword || this.searchKeyword == '') {
        await this._getTableData()
        return
      }
      this.loading = true
      setTimeout(async () => {
        this.loading = false
        const res = await student.getStudentByPage(this.searchKeyword)
        this.tableData = res.items
      }, 1000)
    },

    // 切换分页
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true

      await this._getTableData()
      this.loading = false
    },

    async _getTableData() {
      const currentPage = this.currentPage - 1
      const res = await student.getStudentByPage(null, currentPage, this.pageCount)
      this.tableData = res.items
      this.total_nums = res.total
    },

    handleEdit(val) {
      this.showEdit = true
      this.editStudentID = val.row.id
      this.classId = val.row.class_id
    },
    handleDelete(val) {
      this.$confirm('此操作将永久删除该学生, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await student.deleteStudent(val.row.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          if (this.total_nums % this.pageCount === 1 && this.currentPage !== 1) {
            // 判断删除的是不是每一页的最后一条数据
            this.currentPage--
          }
          await this._getTableData()
          this.$message({
            type: 'success',
            message: `${res.message}`,
          })
        }
      })
    },
    rowClick() {},
    async editClose() {
      this.showEdit = false
      await this._getTableData()
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .header {
    display: flex;
    justify-content: space-between;
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
  }

  .pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px;
  }
}
</style>
