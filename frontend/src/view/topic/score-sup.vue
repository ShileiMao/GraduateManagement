<template>
  <div class="container">
    <div class="header">
      <p class="title">课题补分</p>
    </div>
    <!-- 表格 -->
    <el-table v-loading="loading" :data="tableData" stripe>
      <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
      <el-table-column prop="name" label="名称" width="200"></el-table-column>
      <el-table-column prop="publish_year" label="发布年份" width="100"></el-table-column>
      <el-table-column label="操作" width="400">
        <template slot-scope="scope">
          <el-button @click="handleScore(scope.row)" type="primary" plain size="mini" v-text="btnTextObj.score"
            >指导教师评分</el-button
          >
          <el-button @click="handleAdvice(scope.row, true)" type="info" plain size="mini" v-text="btnTextObj.advice"
            >指导教师评分</el-button
          >
          <el-button @click="handleAdvice(scope.row, false)" type="info" plain size="mini">不留言</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="showAdviseCard" title="留言">
      <el-input v-model="comment" @keyup.enter.native="submitComment" placeholder="请输入留言,回车提交"></el-input>
    </el-dialog>

    <el-dialog title="评分" :visible.sync="showScoreCard" width="60%">
      <el-steps :active="currentStep" finish-status="success">
        <el-step title="选择评分表模板"></el-step>
        <el-step title="填写评分表信息"></el-step>
      </el-steps>

      <div v-show="currentStep == 0">
        <el-form label-width="100px">
          <el-form-item label="评分表模板">
            <el-select v-model="cardId" placeholder="请选择">
              <el-option v-for="item in cards" :key="item.id" :label="item.title" :value="item.id"> </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <div class="cardTable" v-if="cardTable" v-show="currentStep == 1">
        <ol>
          <template v-for="question in cardTable.questions">
            <li style="float:left;width:80%;margin-top: 10px;">
              {{ question.title }}<el-link type="danger">{{ '(共计' + question.max_score + '分)' }}</el-link>
              <ol>
                <template v-for="option in question.options">
                  <li style="margin-top: 5px;">
                    {{ option.content + '' }}<el-link type="info">{{ '(' + option.score + '分)' }}</el-link>
                  </li>
                </template>
              </ol>
            </li>

            <el-input-number
              v-model="scoresObj[question.id]"
              :min="0"
              :max="question.max_score"
              label="评分"
              size="small"
              style="float:right;width:20%;"
            ></el-input-number>
          </template>
        </ol>
      </div>

      <div class="add-dialog">
        <div>
          <el-button v-if="currentStep > 0" style="margin-top: 12px;" @click="prev">上一步</el-button>
        </div>
        <div>
          <el-button style="margin-top: 12px;" @click="showScoreCard = false">取消</el-button>
          <el-button type="primary" style="margin-top: 12px;" @click="next">
            {{ currentStep > 0 ? '完成' : '下一步' }}
          </el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import topic from '@/model/topic'
import user from '@/lin/model/user'
import cardApi from '@/model/card'
import scorecardApi from '@/model/scorecard'

export default {
  data() {
    return {
      loginUsername: '',
      loginUserType: '',
      tableData: [],
      loading: false,
      topicId: null,
      showScoreCard: false,
      showAdviseCard: false,
      cardId: null,
      cardTable: null,
      cards: [],
      currentStep: 0,
      scoresObj: {
        // '999': 0
      },
      btnTextObj: {
        advice: '',
        score: '',
      },
      comment: null,
    }
  },
  async created() {
    await this.checkLoginUser()
    await this.initData()
    this._getTableData()
  },
  methods: {
    async _getTableData() {
      let _topics = null
      let textObj = null
      if (this.loginUserType === 'teacher') {
        _topics = await topic.getSupTopicsByTeacherId(this.loginUsername)
        textObj = {
          score: '指导教师评分',
          advice: '指导教师留言',
        }
      } else if (this.loginUserType === 'judgeTeam') {
        _topics = await topic.getSupTopics()
        textObj = {
          score: '答辩小组评分',
          advice: '答辩小组留言',
        }
      }

      // 过滤掉已经完成的
      if (_topics == null) {
        return
      }

      this.tableData = _topics
      this.btnTextObj = textObj
    },

    prev() {
      if (this.currentStep-- < 1) this.currentStep = 0
    },

    async next() {
      if (this.currentStep === 1) {
        // 完成
        const formData = {
          topic_id: this.topicId,
          scores: this.scoresObj,
          card_id: this.cardId,
          is_supplement: true,
        }
        let res = null
        if (this.loginUserType === 'teacher') {
          // 指导教师评分
          res = await scorecardApi.createByTeacher(formData)
        } else if (this.loginUserType === 'judgeTeam') {
          // 答辩组评分
          const { id } = await scorecardApi.getScorecardByTopicId(this.topicId)
          res = await scorecardApi.updateScorecard(id, formData)
        }

        if (res != null && res.code < window.MAX_SUCCESS_CODE) {
          this.$message.success(`${res.message}`)
          this.showScoreCard = false
          this._getTableData()
          this.restData()
        }
      } else {
        this.currentStep++
        // 加载表数据
        this.cards.forEach(card => {
          if (card.id === this.cardId) {
            this.cardTable = card
          }
        })
      }
    },

    async restData() {
      this.scoresObj = {}
      this.currentStep = 0
      this.cardId = null
      this.cardTable = null
      this.comment = null
    },

    async handleScore(data) {
      this.restData()
      this.topicId = data.id
      this.showScoreCard = true
    },

    handleAdvice(data, flag) {
      this.topicId = data.id
      if (flag) this.showAdviseCard = true
      else {
        this.submitComment()
      }
    },
    async submitComment() {
      const scorecard = await scorecardApi.getScorecardByTopicId(this.topicId)
      if (Object.keys(scorecard).length === 0) {
        return
      }

      let res = null
      if (this.btnTextObj.advice === '指导教师留言') {
        res = await scorecardApi.guideTeacherComment(scorecard.id, this.comment, this.comment != null)
      } else if (this.btnTextObj.advice === '答辩小组留言') {
        res = await scorecardApi.judgeTeamComment(scorecard.id, this.comment, this.comment != null)
      }

      if (res != null && res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success('评论成功')
        this._getTableData()
        this.restData()
        this.showAdviseCard = false
      }
    },

    async initData() {
      if (!window.sessionStorage.getItem('cards')) {
        const res = await cardApi.getAllCardWithOptions()
        this.cards = res
        window.sessionStorage.setItem('cards', JSON.stringify(res))
      } else {
        this.cards = JSON.parse(window.sessionStorage.getItem('cards'))
      }
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
      re = new RegExp('答辩组账号')
      if (re.test(result.username)) {
        this.loginUserType = 'judgeTeam'
      }
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
