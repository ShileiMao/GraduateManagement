import { get, post, put, _delete } from '@/lin/plugin/axios'

class Essay {
    static async getEssayList(graduateId, teacherId, studentId) {
        let queryStr = `graduateInfoId=${graduateId}`
        if(teacherId != null) {
            queryStr = queryStr.concat(`&teacherId=${teacherId}`)
        }
        if(studentId != null) {
            queryStr = queryStr.concat(`&studentId=${studentId}`)
        }
        let url = `/v1/topicEssay/getEssaies?${queryStr}`

        console.log("get essay list from url: " + url)
        let res = await get(url)
        console.log("essay list: " + JSON.stringify(res))
        
        return res
    }

    static async getEssay(id) {
        let url = `/v1/topicEssay/getEssay?essayId=${id}`
        let res = await get(url)
        return res;
    }
    
    static async uploadEssay(data) {
        let url = `/v1/topicEssay/uploadEssay`
        
        const res = await post(url, data)
        return res
    }

    static async updateExistingEssay(data) {
        let url = `/v1/topicEssay/modifyEssay`
        let res = await post(url, data);
        return res;
    }
}

export default Essay