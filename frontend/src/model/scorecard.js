import { get, post, put, _delete } from '@/lin/plugin/axios'

class Scorecard {
  static async createByTeacher(data) {
    const res = await post('v1/scorecard', data)
    return res
  }

  static async deleteScorecard(id) {
    const res = await _delete(`v1/scorecard/${id}`)
    return res
  }

  static async getScorecard(id) {
    const res = await get(`v1/scorecard/${id}`)
    console.log("score card: " + JSON.stringify(res))
    return res
  }

  static async getScroecardByParams(data) {
    const res = await get(`v1/scorecard/getScoreCard`, data)
    return res
  }

  static async getScorecardIdBySid(sid) {
    const res = await get(`v1/scorecard/by/student/${sid}`)
    return res
  }

  static async updateScorecard(id, data) {
    const res = await put(`v1/scorecard/${id}`, data)
    return res
  }

  static async getScorecardByPage(page = 0, count = 10, query) {
    const { teacherId, graduateInfoId } = query
    const res = await get('v1/scorecard/page', { page, count, teacherId, graduateInfoId })
    return res
  }

  static async getScorecardByTopicId(topicId) {
    const res = await get(`v1/scorecard/by/topic/${topicId}`)
    return res
  }

  static async getAllComplete() {
    const res = await get('v1/scorecard/all/complete')
    return res
  }

  static async existsByTopicId(topicId) {
    await get(`v1/scorecard/exists/by/topic/${topicId}`)
  }

  static async getAllGuideComplete() {
    const res = await get('v1/scorecard/all/guide/complete')
    return res
  }

  static async getAllGuideAdviseComplete() {
    const res = await get('v1/scorecard/all/guide/advise/complete')
    return res
  }

  static async guideTeacherComment(id, data, isComment) {
    let baseUrl = `v1/scorecard/${id}/guide/comment`
    if (isComment) {
      baseUrl += `?comment=${data}`
    }
    const res = await put(baseUrl)
    return res
  }

  static async judgeTeamComment(id, data, isComment) {
    let baseUrl = `v1/scorecard/${id}/judge/comment`
    if (isComment) {
      baseUrl += `?comment=${data}`
    }
    const res = await put(baseUrl)
    return res
  }

  static async committeeComment(id, data, isComment) {
    let baseUrl = `v1/scorecard/${id}/committee/comment`
    if (isComment) {
      baseUrl += `?comment=${data}`
    }
    const res = await put(baseUrl)
    return res
  }
}

export default Scorecard
