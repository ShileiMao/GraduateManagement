<template>
  <div>
    <div class="container">
      <div class="header">
        <p class="title">专业列表</p>
      </div>
      <div class="toolbox">
        <div class="toolbox-left">
          <!-- <el-upload
            :show-file-list="false"
            :before-upload="beforeUpload"
            :on-success="onSuccess"
            :on-error="onError"
            :disabled="importDataDisabled"
            action="/employee/basic/import"
          >
            <el-button :disabled="importDataDisabled" type="primary" :icon="importDataBtnIcon">
              {{ importDataBtnText }}
            </el-button>
          </el-upload> -->
          <div style="margin-left:10px">
            <el-button @click="handleAdd" type="primary">新增</el-button>
          </div>
        </div>
        <div class="toolbox-right">
          <lin-search @query="onQueryChange" placeholder="请输入专业名称" />
          <div style="margin-left:30px">
            <el-select @change="handleChange" size="medium" clearable v-model="collegeID" placeholder="请选择学院">
              <el-option v-for="item in colleges" :key="item.id" :label="item.name" :value="item.id"> </el-option>
            </el-select>
          </div>
        </div>
      </div>

      <!-- 表格 -->
      <el-table v-loading="loading" :data="tableData" stripe>
        <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" width="120"></el-table-column>
        <el-table-column prop="college_id" :formatter="collegeFormatter" label="所属学院" width="120"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope)" type="primary" plain size="mini">编辑</el-button>
            <el-button @click="handleDelete(scope)" type="danger" size="mini" plain>删除</el-button>
          </template>
        </el-table-column>
      </el-table>
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

    <el-dialog :title="isShowAdd ? '新增专业' : '编辑专业'" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="form">
        <el-form-item
          prop="name"
          :rules="[{ required: true, message: '请输入专业名称', trigger: 'blur' }]"
          label="专业名称"
        >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          prop="college_id"
          :rules="[{ required: true, message: '请选择学院', trigger: 'blur' }]"
          label="所属学院"
        >
          <el-select size="medium" v-model="form.college_id" placeholder="请选择学院">
            <el-option v-for="item in colleges" :key="item.id" :label="item.name" :value="item.id"> </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import LinSearch from '@/component/base/search/lin-search'
import major from '@/model/major'
import college from '@/model/college'

export default {
  components: {
    LinSearch,
  },
  data() {
    return {
      tableData: [],
      loading: false,
      importDataBtnText: '导入数据',
      importDataDisabled: false,
      importDataBtnIcon: 'el-icon-upload2',
      dialogFormVisible: false,
      form: {
        id: null,
        name: '',
        college_id: null,
      },
      isShowAdd: true,
      // 分页相关
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
      currentPage: 1, // 默认获取第一页的数据
      pageCount: 10, // 每页10条数据
      total_nums: 180, // 分组内的用户总数
      searchKeyword: '',
      colleges: [],
      collegeID: '',
    }
  },
  async created() {
    this.loading = true
    await this.getAllCollege()
    await this._getTableData((this.currentPage - 1) * this.pageCount, this.pageCount)
    this.loading = false
  },
  methods: {
    async _getTableData() {
      const currentPage = this.currentPage - 1
      const res = await major.getMajorByPage(this.collegeID, currentPage, this.pageCount)
      this.tableData = res.items
      this.total_nums = res.total
    },
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
      this.getStudents()
    },
    onError(err, file, fileList) {
      this.importDataBtnText = '导入数据'
      this.importDataBtnIcon = 'el-icon-upload2'
      this.importDataDisabled = false
    },

    // 增加或更新成功后处理函数
    addInit(res, message) {
      if (res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success(message)
        this.dialogFormVisible = false
        this._getTableData()
        this.refreshPagination = false // 刷新pagination组件
        this.$nextTick(() => {
          this.refreshPagination = true
        })
      }
      this.loading = false
    },

    async submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          this.loading = true
          if (this.isShowAdd) {
            const res = await major.createMajor(this.form)
            this.addInit(res, '增加成功')
          } else {
            const res = await major.updateMajor(this.form)
            this.addInit(res, '更新成功')
          }
        } else {
          this.$message.error('请填写正确的信息')
        }
      })
    },

    async getAllCollege() {
      const colleges = await college.getAllCollege()
      this.colleges = colleges
    },

    // 下拉框选择
    async handleChange() {
      this.currentPage = 1
      this.loading = true
      await this._getTableData()
      this.loading = false
    },

    // 切换分页
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true

      await this._getTableData()
      this.loading = false
    },

    collegeFormatter(row, column) {
      let result = '学院不存在'
      for (const item of this.colleges) {
        if (row.college_id == item.id) {
          result = item.name
          break
        }
      }
      return result
    },

    // 搜索
    async onQueryChange(query) {
      this.searchKeyword = query.trim()
      if (!query) {
        this._getTableData()
        return
      }
      this.loading = true
      setTimeout(async () => {
        this.loading = false
        const res = await major.searchMajors(this.searchKeyword)
        this.tableData = res
      }, 1000)
    },

    handleImport(val) {},

    handleAdd() {
      this.isShowAdd = true
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.form.clearValidate()
      })
      this.form = {
        id: null,
        name: '',
        college_id: null,
      }
    },

    handleEdit(val) {
      this.isShowAdd = false
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.form.clearValidate()
      })

      this.form = {
        id: val.row.id,
        name: val.row.name,
        college_id: val.row.college_id,
      }
    },

    handleDelete(val) {
      this.$confirm('此操作将永久删除该专业, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await major.deleteMajor(val.row.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          if (this.total_nums % this.pageCount === 1 && this.currentPage !== 1) {
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
