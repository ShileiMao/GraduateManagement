<template>
    <div class="wrap">
        <div class="container">
          <div class="header">
            <p class="title">论文查重</p>
          </div>

          <el-form ref="form" :rules="rules" :model="paperForm" label-position="float">
            <el-form-item label="论文文本：" prop="sourceText">
                <textarea class="textArea" v-model="paperForm.sourceText" placeholder="输入文本"></textarea>
            </el-form-item>

            <el-form-item label="参考文本：" prop="refText">
                <textarea class="textArea" v-model="paperForm.refText" placeholder="输入文本"></textarea>
            </el-form-item>

            <el-button type="primary" @click="checkPaper">确 定</el-button>
          </el-form>

        
            <div v-if="showResult">
                <p >查重结果：</p>
                <p>相似度: {{this.similarityPercent}}</p>
            </div>
        </div>
    </div>
</template>
<script>
import { get, post, put, _delete } from '@/lin/plugin/axios'

export default {
    
    data() {
        // 验证回调函数
        const checkPaperLength = (rule, value, callback) => {
            // eslint-disable-line
            if (!value) {
                callback(new Error('请输入论文原文'))
            }
            if(value.length < 200) {
                callback(new Error('请输入论文原文'))
            }
            callback()
        }

        return {
            paperForm: {
                sourceText: '',
                refText: ''
            },
            rules: {
                sourceText: {
                    validator: checkPaperLength,
                    trigger: ['blur', 'change'],
                    required: true,
                },
                refText: {
                    validator: checkPaperLength,
                    trigger: ['blur', 'change'],
                    required: true,
                }
            },
            similarity: 0,
            similarityPercent: '',
            showResult: false
        }
    },
    setup() {
        
    },
    created() {
        console.log("created paper check form")
    },
    methods: {
        async checkPaper() {
            // console.log("source text: " + this.paperForm.sourceText)
            // console.log("ref text: " + this.paperForm.refText)

            const data = {paperText: this.paperForm.sourceText, refText: this.paperForm.refText}
            const res = await post('v1/papercheck/check', data)
            console.log("response: " + res)

            const value = parseFloat(res)
            this.similarity = value
            this.similarityPercent = (value * 100).toFixed(2) + "%"
            this.showResult = true
        }
    }
}
</script>

<style lang="scss" scoped>
.container {
  .title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    // text-indent: 40px;

    .back {
      float: right;
      margin-right: 40px;
      cursor: pointer;
    }
  }

  .wrap {
    padding: 20px;
    justify-content: space-between;
  }

  .textArea {
      width: 80%;
      height: 300px;
  }

  .submit {
    float: left;
  }
}

</style>