<template>
  <div>
    <div class="container">
      <div class="header">
        <p class="title">评分表模板列表</p>
      </div>
      <div class="toolbox">
        <div class="toolbox-left">
          <el-button style="margin-left:30px;" @click.prevent="showAddDialog = true" type="primary" plain size="medium"
            >添加评分表模板</el-button
          >
        </div>
      </div>

      <el-card v-for="card in cards" shadow="hover" class="box-card">
        <div slot="header" class="clearfix">
          <span style="color:#67C23A">{{ card.title }}</span>
          <el-button
            @click="handleDelete(card.id)"
            style="float: right;"
            type="danger"
            icon="el-icon-delete"
            circle
          ></el-button>
        </div>

        <el-collapse accordion>
          <template v-for="(question, index) in card.questions">
            <el-collapse-item :title="question.title + ' (共计' + question.max_score + ')'">
              <ul>
                <li v-for="option in question.options">{{ option.content + '(' + option.score + '分)' }}</li>
              </ul>
            </el-collapse-item>
          </template>
        </el-collapse>
      </el-card>

      <el-dialog :before-close="clearFormData" title="新增" :visible.sync="showAddDialog" width="30%">
        <el-form ref="form" :model="cardForm" label-width="80px">
          <el-form-item label="标题名称">
            <el-input v-model="cardForm.title"></el-input>
          </el-form-item>
          <el-checkbox-group v-model="checkQuestionIdList" :min="1">
            <el-checkbox v-for="question in questions" :label="question.id" :key="question.id">{{
              question.title
            }}</el-checkbox>
          </el-checkbox-group>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="showAddDialog = false">取 消</el-button>
          <el-button type="primary" @click="handleCreate">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import cardApi from '@/model/card'
import questionApi from '@/model/question'

export default {
  data() {
    return {
      cardForm: {
        title: null,
        question_ids: null,
      },
      showAddDialog: false,
      cards: null,

      questions: null,
      checkQuestionIdList: [],
    }
  },
  async created() {
    await this.initData()
    console.log("score card list created");
  },
  methods: {
    clearFormData() {
      this.showAddDialog = false
      this.cardForm = {
        title: null,
        question_ids: null,
      }
      this.checkQuestionIdList = []
    },
    async handleCreate() {
      this.showAddDialog = false
      let questionIds = ''
      this.checkQuestionIdList.map(id => {
        questionIds += `${id },`
      })
      this.cardForm.question_ids = questionIds.slice(0, questionIds.length - 1)
      const res = await cardApi.createCard(this.cardForm)
      if (res.code < window.MAX_SUCCESS_CODE) {
        await this.initCards()
        this.$message({
          type: 'success',
          message: '新增成功',
        })
      } else {
        this.$message({
          type: 'error',
          message: `${res.message}`,
        })
      }
    },

    handleDelete(val) {
      this.$confirm('此操作将永久删除该评分表模板, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await cardApi.deleteCard(val)
        if (res.code < window.MAX_SUCCESS_CODE) {
          await this.initCards()
          this.$message({
            type: 'success',
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

    async initCards() {
      const res = await cardApi.getAllCardWithOptions()
      this.cards = res
      window.sessionStorage.setItem('cards', JSON.stringify(res))
    },

    async initData() {
      if (!window.sessionStorage.getItem('cards')) {
        const res = await cardApi.getAllCardWithOptions()
        this.cards = res
        window.sessionStorage.setItem('cards', JSON.stringify(res))
      } else {
        this.cards = JSON.parse(window.sessionStorage.getItem('cards'))
      }

      if (!window.sessionStorage.getItem('questions')) {
        const res = await questionApi.getAllQuestion()
        this.questions = res
        window.sessionStorage.setItem('questions', JSON.stringify(res))
      } else {
        this.questions = JSON.parse(window.sessionStorage.getItem('questions'))
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: '';
  }
  .clearfix:after {
    clear: both;
  }

  .box-card {
    width: 400px;
    float: left;
    margin: 10px 10px;
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
