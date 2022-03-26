<template>
  <div class="container">
    <div class="header">
      <p class="title">评分准则列表</p>
    </div>
    <div class="toolbox">
      <div class="toolbox-left">
        <el-button style="margin-left:30px;" @click.prevent="showAddDialog = true" type="primary" plain size="medium"
          >添加评分准则</el-button
        >
      </div>
    </div>
    <!-- 表格 -->
    <el-table v-loading="loading" :data="tableData" stripe>
      <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
      <el-table-column prop="title" label="评分标准名" width="120"></el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button @click="showOptions(scope.row)" type="info" plain size="mini">选项信息</el-button>
          <!-- <el-button @click="handleEdit(scope.row)" type="primary" plain size="mini">编辑</el-button> -->
          <el-button @click="handleDelete(scope.row)" type="danger" size="mini" plain>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="pagination">
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

    <el-dialog title="评分准则选项" :visible.sync="showOptionDialog">
      <el-table :data="optionData">
        <el-table-column property="content" label="选项内容" width="350"></el-table-column>
        <el-table-column property="score" label="权重" width="200"></el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog title="评分准则选项" :visible.sync="showAddDialog">
      <el-steps :active="currentStep" finish-status="success">
        <el-step title="填写准则信息"></el-step>
        <el-step title="填写准则选项信息"></el-step>
      </el-steps>

      <div v-show="currentStep == 0">
        <el-form :inline="true" :model="questionForm" class="demo-form-inline">
          <el-form-item prop="title" label="准则标题:">
            <el-input v-model="questionForm.title" placeholder="准则标题"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <div v-show="currentStep == 1">
        <el-form :inline="true" class="demo-form-inline">
          <template v-for="idx in 4">
            <el-form-item :label="'准则选项' + idx">
              <el-input v-model="questionForm.options[idx - 1].content" size="small" placeholder="内容"></el-input>
              <el-input v-model="questionForm.options[idx - 1].score" size="small" placeholder="权重"></el-input>
            </el-form-item>
          </template>
        </el-form>
      </div>

      <div class="add-dialog">
        <div>
          <el-button v-if="currentStep > 0" style="margin-top: 12px;" @click="prev">上一步</el-button>
        </div>
        <div>
          <el-button style="margin-top: 12px;" @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" style="margin-top: 12px;" @click="next">
            {{ currentStep > 0 ? '完成' : '下一步' }}
          </el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import question from '@/model/question'

export default {
  data() {
    return {
      currentStep: 0,
      tableData: [],
      optionData: [],
      loading: false,
      isCreate: false,
      questionId: null,
      showEdit: false,
      // 分页相关
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
      currentPage: 1, // 默认获取第一页的数据
      pageCount: 10, // 每页10条数据
      totalNums: 180, // 分组内的用户总数
      showOptionDialog: false,
      showAddDialog: false,
      questionForm: {
        title: '',
        options: [
          { content: '', score: '' },
          { content: '', score: '' },
          { content: '', score: '' },
          { content: '', score: '' },
        ],
      },
    }
  },
  async created() {
    this.loading = true
    this._getTableData((this.currentPage - 1) * this.pageCount, this.pageCount)
    this.loading = false
  },
  methods: {
    async _getTableData() {
      const currentPage = this.currentPage - 1
      const res = await question.getQuestionByPage(currentPage, this.pageCount)
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
    addQuestion() {},

    handleEdit(val) {
      this.isCreate = false
      this.questionId = val.id
      this.showEdit = true
    },

    async showOptions(val) {
      this.showOptionDialog = true
      this.optionData = await question.getOptionsByQuestion(val.option_ids)
      console.log(this.optionData)
    },

    prev() {
      if (this.currentStep-- < 1) this.currentStep = 0
    },

    async next() {
      if (this.currentStep === 1) {
        // 完成
        console.log(this.questionForm)
        const res = await question.createQuestion(this.questionForm)
        if (res.code < window.MAX_SUCCESS_CODE) {
          this.$message.success(`${res.message}`)
          this.showAddDialog = false
          this._getTableData()
          this.restData()
        }
      } else {
        this.currentStep++
      }
    },

    async restData() {
      this.currentStep = 0
      this.questionForm = {
        title: '',
        options: [
          { content: '', score: '' },
          { content: '', score: '' },
          { content: '', score: '' },
          { content: '', score: '' },
        ],
      }
      const res = await question.getAllQuestion()
      window.sessionStorage.setItem('questions', JSON.stringify(res))
    },

    handleDelete(val) {
      this.$confirm('此操作将永久删除该评分标准, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await question.deleteQuestion(val.id)
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

  .add-dialog {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

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
