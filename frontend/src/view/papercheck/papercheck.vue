<template>
  <div class="wrap">
    <div class="container">
      <div class="header">
        <p class="title">论文查重</p>
      </div>

      <div v-if="hasResult">
        <div class="toolbox">
          <label class="toolbox-right">
                
          </label>
          <el-button class="toolbox-right" size="medium" type="primary" @click="back">
            返 回
          </el-button>
        </div>
        <!-- 表格 查重结果 -->
        <el-table :data="tableData" stripe>
          <el-table-column prop="name" label="报告" width="200"></el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <label><a  target="_blank" :href="scope.row.path">查 看</a></label>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-else>
        <el-form ref="form" :model="form" label-width="120px" style="width: 50%">
          <el-form-item label="论文" 
          :rules="[{ required: true, message: '请输入描述信息', trigger: 'blur' }]">
            <input type="file" id="file" hidden @change="fileSelected" />
            <el-input placeholder="请选择论文" v-model="form.essayFileName" class="input-with-select" disabled>
              <el-button slot="append" icon="el-icon-folder" type="success" @click="selectFile"></el-button>
            </el-input>
          </el-form-item>
          <el-button type="primary" @click="submitForm('form')">确 定</el-button>
        </el-form>
      </div>

    </div>
  </div>
</template>
<script>

import { get, post, put, _delete } from '@/lin/plugin/axios'
import stringUtils from '@/model/stringUtils'
import config from '@/config/index'

export default {
  data() {
    return {
      hasResult: false,
      tableData: [],
      form: {
        essayFileName: '',
        essayContent: '',
      },
    }
  },
  setup() {},
  created() {
    console.log('created paper check form')
  },
  methods: {
    selectFile(e) {
      const fu = document.getElementById('file')
      if (fu == null) return
      fu.click()
    },

    async fileSelected(e) {
      console.log('target: ' + e.target)
      try {
        var file = e.target.files[0];
        this.docPath = file.name

        console.log("file selected: " + file)

        if (!file) {
          return;
        }
        
        let buffer = await file.arrayBuffer()
        let str = stringUtils.arrayBufferToBase64(buffer)

        console.log("base64 string: " + str)
        console.log("file name： " + file.name)
        
        this.form.essayFileName = file.name
        this.form.essayContent = str
      } catch (error) {
        console.debug('choice file err:', error)
      }
    },
    async submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          const postData = { 
            fileContent: this.form.essayContent,
            fileName: this.form.essayFileName
          }

          console.log("posting data: " + JSON.stringify(postData))
          const res = await post('/v1/papercheck/check', postData)

          console.log("post check response: " + JSON.stringify(res))

          this.tableData = res.output_paths.map( item => {
            const newItem = {}
            newItem.name = item.name 
            newItem.path = config.baseURL + "/" + item.path
            return newItem
          })
          this.hasResult = true

          console.log("table data: " + JSON.stringify(this.tableData))
        }
      })
    },

    back() {
      this.hasResult = false
    },
  },
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