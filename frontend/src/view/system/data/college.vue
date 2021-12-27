<template>
  <div>
    <div class="container">
      <div class="header"><div class="title">学院列表</div></div>
      <div class="toolbox">
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
        <el-button @click="handleAdd" style="margin-left:10px" type="primary">新增</el-button>
      </div>

      <!-- 表格 -->
      <el-table :data="tableData" stripe>
        <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" width="120"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope)" type="primary" plain size="mini">编辑</el-button>
            <el-button @click="handleDelete(scope)" type="danger" size="mini" plain>删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog :title="isShowAdd ? '新增学院' : '编辑学院'" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="form">
        <el-form-item
          prop="name"
          :rules="[{ required: true, message: '请输入学院名称', trigger: 'blur' }]"
          label="学院名称"
        >
          <el-input v-model="form.name" autocomplete="off"></el-input>
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
import college from '@/model/college'

export default {
  data() {
    return {
      tableColumn: [{ prop: 'id', label: 'id' }, { prop: 'name', label: '名称' }],
      tableData: [],
      importDataBtnText: '导入数据',
      importDataDisabled: false,
      importDataBtnIcon: 'el-icon-upload2',
      dialogFormVisible: false,
      form: {
        id: '',
        name: '',
      },
      isShowAdd: true,
    }
  },
  async created() {
    this.loading = true
    await this.getColleges()
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
      this.getStudents()
    },
    onError(err, file, fileList) {
      this.importDataBtnText = '导入数据'
      this.importDataBtnIcon = 'el-icon-upload2'
      this.importDataDisabled = false
    },

    async submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          if (this.isShowAdd) {
            this.loading = true
            const res = await college.createCollege(this.form)
            this.loading = false
            if (res.code < window.MAX_SUCCESS_CODE) {
              this.$message.success('添加成功')
              this.dialogFormVisible = false
              this.getColleges()
            }
          } else {
            this.loading = true
            const res = await college.updateCollege(this.form)
            this.loading = false
            if (res.code < window.MAX_SUCCESS_CODE) {
              this.$message.success('更新成功')
              this.dialogFormVisible = false
              this.getColleges()
            }
          }
        } else {
          this.$message.error('请填写正确的信息')
        }
      })
    },

    async getColleges() {
      try {
        const colleges = await college.getAllCollege()
        this.tableData = colleges
      } catch (error) {
        if (error.code === 10020) {
          this.tableData = []
        }
      }
    },

    handleImport(val) {},

    handleAdd() {
      this.isShowAdd = true
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.form.resetFields()
      })
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
      }
    },

    handleDelete(val) {
      this.$confirm('此操作将永久删除该学院, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await college.deleteCollege(val.row.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.getColleges()
          this.$message({
            type: 'success',
            // message: `${res.message}`,
            message: '删除成功',
          })
        }
      })
    },
    rowClick() {},
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
    justify-content: flex-start;
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
