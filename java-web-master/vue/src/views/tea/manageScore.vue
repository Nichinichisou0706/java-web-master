<template>
  <div>
    <!--搜索框-->
    <div style="margin-bottom: 15px">
      <el-input style="width: 180px; margin-left: 5px" placeholder="请输入用户名" v-model="params.studentId"></el-input>
      <el-select v-model="params.level" style="margin-left: 5px; width: 100px" placeholder="请选择">
        <el-option label="销售情况" value=""></el-option>
        <el-option label="极低" value="1"></el-option>
        <el-option label="低" value="2"></el-option>
        <el-option label="中" value="3"></el-option>
        <el-option label="高" value="4"></el-option>
        <el-option label="极高" value="5"></el-option>
      </el-select>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
    </div>

    <el-table :data="tableData" border stripe style="width: 450px">
      <el-table-column sortable prop="studentId" label="用户名" ></el-table-column>
      <el-table-column prop="studentName" label="姓名"></el-table-column>
      <el-table-column sortable prop="score" :formatter="scoreFormat" label="价格" ></el-table-column>
      <el-table-column fixed="right" label="操作" width="75px">
        <template #default="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 50px">
      <div id="main" style="width:900px ;height:300px;"></div>
    </div>



    <!--编辑时的弹窗-->
    <el-dialog title="编辑价格" :visible.sync="dialogVisible" width="30%" >
      <el-form :inline="true" :model="form" label-width="120px" :rules="rules" ref="ruleForm">
        <el-form-item label="用户名">
          <el-input v-model="form.studentId" readonly style="width: 75%"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.studentName" readonly  style="width: 75%"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="score">
          <el-input v-model="form.score" style="width: 75%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
              <span class="dialog-footer">
                <el-button @click="closeDialog">取消</el-button>
                <el-button type="primary" @click="update">确认</el-button>
              </span>
      </template>
    </el-dialog>

  </div>

</template>

<script>

import request from "../../utils/request";
import Cookies from 'js-cookie';
import * as echarts from 'echarts';


export default {
  name: "manageScore",
  data() {

    // 验证是否是[0-100]的整数
    const checkScore = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('输入不可以为空'))
      }
      setTimeout(() => {
        if (!Number(value) && value !== 0) {
          callback(new Error('请输入[0,100]之间的整数'))
        } else {
          const re = /^[0-9]*[1-9][0-9]*$/;
          const rsCheck = re.test(value);
          if (!rsCheck) {
            callback(new Error('请输入[0,100]之间的整数'));
          }
          if (value < 0 || value > 100) {
            callback(new Error('请输入[0,100]之间的整数'));
          } else {
            callback();
          }
        }
      }, 100)
    };


    return {
      user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
      teachId: '',
      tableData: [],
      form: {},
      dialogVisible: false,
      params: {
        id: '',
        studentId: '',
        level: '',
      },
      rules: {
        score: [
          { validator: checkScore, trigger: 'blur' }
        ],
      },
      length1: '',
      length3: '',
      length4: '',
      length5: '',

    }


  },

  mounted: async function() {
    await this.Init();
    this.$nextTick(function() {
      let data = [
        {name: '低', value: this.length1},
        {name: '中', value: this.length3},
        {name: '高', value: this.length4},
        {name: '极高', value: this.length5},
      ]
      this.getPie(data);
    })
  },

  created() {
    const id = this.$route.query.id;
    this.params.id = id;
    if (id === '' || id == null){
      this.$notify.warning("请从价格管理进入该页面哦~")
    } else {
      this.load();
    }
  },

  methods: {

    load() {
      request.get("/tea/manageScore", {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data;
        } else {
          alert(res.msg);
        }
      })
    },

    async Init() {
      await this.init1();
      await this.init3();
      await this.init4();
      await this.init5();
    },

    async init1() {
      this.params.level = 1;
      await request.get("/tea/manageScore", {params: this.params}).then(res => {
        this.length1 = res.data.length;
      });
      this.params.level = '';
    },

    async init3() {
      this.params.level = 3;
      await request.get("/tea/manageScore", {params: this.params}).then(res => {
        this.length3 = res.data.length;
      });
      this.params.level = '';
    },

    async init4() {
      this.params.level = 4;
      await request.get("/tea/manageScore", {params: this.params}).then(res => {
        this.length4 = res.data.length;
      });
      this.params.level = '';
    },

    async init5() {
        this.params.level = 5;
        await request.get("/tea/manageScore", {params: this.params}).then(res => {
          this.length5 = res.data.length;
        });
        this.params.level = '';
    },

    handleEdit(row) {
      if (row.score === -1) {
        row.score = '';
      }
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },

    closeDialog() {
      this.dialogVisible = false;
      this.load();
    },

    scoreFormat(row) {
      return row.score === -1 ? '价格未录入' : row.score;
    },

    update() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post("/tea/update", this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("编辑成功");
              this.$refs['ruleForm'].resetFields();
              this.dialogVisible = false;
              this.load();
            } else {
              this.$notify.error(res.msg);
            }
          })
        }
      })
    },

    getPie(data) {
      // 绘制图表
      let myChart = echarts.init(document.getElementById('main'));
      // 指定图表的配置项和数据
       let option = {
          //标题
          title: {
            text: '价格扇形图',
            x: 'left',              //标题位置
            textStyle: { //标题内容的样式
              color: '#000',
              fontStyle: 'normal',
              fontWeight: 100,
              fontSize: 16 //主题文字字体大小，默认为18px
            },
          },
          // stillShowZeroSum: true,
          //鼠标划过时饼状图上显示的数据
          tooltip: {
            trigger: 'item',
            formatter: '{a}<br/>{b}:{c} ({d}%)'
          },
          //图例
          legend: {//图例  标注各种颜色代表的模块
            // orient: 'vertical',//图例的显示方式  默认横向显示
            bottom: 10,//控制图例出现的距离  默认左上角
            left: 'center',//控制图例的位置
            textStyle: {//图例中文字的样式
              color: '#000',
              fontSize: 16
            },
            data: ['低', '中', '高', '极高']  //图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: ['#32dadd', '#b6a2de', '#5ab1ef', '#faaaef'],
          // 饼图数据
          series: {
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: '70%',           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            data: data,
            itemStyle: {
              label: {
                show: true,//饼图上是否出现标注文字 标注各模块代表什么  默认是true
                // position: 'inner',//控制饼图上标注文字相对于饼图的位置  默认位置在饼图外
              },
              labelLine: {
                show: true  //官网demo里外部标注上的小细线的显示隐藏    默认显示
              }
            }
          },
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },

  }
}
</script>

<style scoped>

</style>