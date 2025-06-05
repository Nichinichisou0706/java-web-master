<template>
    <div>

        <!--头部区域-->
        <div style="height: 60px; line-height: 60px; background-color: white; margin-bottom: 2px; display: flex" >
            <div style="width: 600px">
                <img src="@/assets/logo2.png" alt="" style="width: 200px; position: relative; top: 10px; left: 20px">
                <span style="margin-left: 40px; font-size: 24px">京城漫游旅游网站</span>
            </div>
            <div style="flex: 1; text-align: right; padding-right: 20px">
                <el-image
                        style="width: 30px; height: 30px; margin-top: 5px; margin-right: 3px "
                        :src="this.user.cover"
                        :preview-teleported="true">
                </el-image>
                <el-dropdown>
                    <el-button size="mini" style="cursor: pointer; margin-top: 5px">
                        {{user.name}}<i class="el-icon-arrow-down el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu  slot="dropdown" style="margin-top: -5px">
                        <el-dropdown-item><div style="width: 70px; text-align: center" @click="info">个人信息</div></el-dropdown-item>
                        <el-dropdown-item><div style="width: 70px; text-align: center" @click="logout">退出</div></el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>

        <!--侧边栏和主体-->
        <div style="display: flex">

            <!--左侧侧边栏导航-->
            <div style="width: 200px; min-height: calc(100vh - 62px); overflow: hidden; margin-right: 2px; background-color: white">
                <el-menu :default-active="$route.path" router class="el-menu-demo">
                    
                    <el-submenu index="/user" v-if="user.role === '管理员'">
                        <template slot="title">
                            <i class="el-icon-user"></i>
                            <span>用户管理</span>
                        </template>
                        <el-menu-item index="/user/add"><i class="el-icon-document-add"></i> 添加用户 </el-menu-item>
                        <el-menu-item index="/user/list"><i class="el-icon-user"></i> 用户列表 </el-menu-item>
                        <el-menu-item index="/user/update"><i class="el-icon-warning"></i> 编辑用户 </el-menu-item>
                    </el-submenu>
                    <el-submenu index="/course" v-if="user.role === '管理员'">
                        <template slot="title">
                            <i class="el-icon-user"></i>
                            <span>景点管理</span>
                        </template>
                        <el-menu-item index="/course/add"><i class="el-icon-document-add"></i> 添加景点 </el-menu-item>
                        <el-menu-item index="/course/list"><i class="el-icon-user"></i> 景点列表 </el-menu-item>
                        <el-menu-item index="/teach/list"><i class="el-icon-goods"></i> 购买列表 </el-menu-item>
                        <el-menu-item index="/course/update"><i class="el-icon-warning"></i> 编辑景点信息 </el-menu-item>

                    </el-submenu>
                    <el-menu-item index="/home" v-if="user.role === '用户'">
                        <i class="el-icon-eleme"></i>
                        <span>首页</span>
                    </el-menu-item>
                    
                    <el-submenu index="/stu" v-if="user.role === '用户'">
                        <template slot="title">
                            <i class="el-icon-user"></i>
                            <span>用户中心</span>
                        </template>
                        
                        <el-menu-item index="/stu/myCourse"><i class="el-icon-user"></i> 我的行程 </el-menu-item>
                        <el-menu-item index="/stu/myScore"><i class="el-icon-user"></i> 我的购买 </el-menu-item>
                    </el-submenu>
                    <el-submenu index="/tea" v-if="user.role === '商家'">
                        <template slot="title">
                            <i class="el-icon-user"></i>
                            <span>景点中心</span>
                        </template>
                        <el-menu-item index="/tea/myCourse"><i class="el-icon-user"></i> 景点信息 </el-menu-item>
                        <el-menu-item index="/tea/score"><i class="el-icon-warning"></i> 商品录入 </el-menu-item>
                        <el-menu-item index="/tea/manageScore"><i class="el-icon-warning"></i> 商品管理 </el-menu-item>

                    </el-submenu>
                </el-menu>
            </div>

            <!--主体数据-->
            <div style="flex: 1; background-color: white; padding: 10px;">
                <router-view/>
            </div>

        </div>


    </div>
</template>

<script>
    import Cookies from 'js-cookie';
    export default {
        name: "layout",
        data() {
            return {
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {}
            }
        },

        created() {
            this.user = JSON.parse(Cookies.get('user'));
        },

        methods: {

            info(){
                this.$router.push('/user/info?id=' + this.user.id);
            },

            logout(){
                Cookies.remove('user');
                this.$router.push('/login');
            }
        }
    }
</script>

<style scoped>

</style>