<template>
  <div>

    <!-- 轮播图 -->
    <el-carousel :interval="5000" arrow="always">
      <el-carousel-item v-for="(image, index) in carouselImages" :key="index">
        <img :src="image" alt="旅游图片" style="width: 100%; height: 400px; object-fit: cover;">
      </el-carousel-item>
    </el-carousel>

    <p>
        京城漫游，一触即达——探索古都魅力，乐享智慧旅程！
    </p>
    <p style="color:#42b983">
        Explore Beijing, One Tap Away — Discover Ancient Charms, Enjoy Smart Travel!
    </p>
    <br>
    <p>
        短暂的逃离西西弗斯式生活，让身心和时间属于自己。
    </p>
    <div class="links-link">
    <a href="https://www.bupt.edu.cn/" target="_blank">北京邮电大学</a>
    </div>
    <!-- 底部信息 -->
    <el-footer style="background-color: #333; color: white; text-align: center; padding: 20px;">
      <p>版权所有 &copy; 2024 京城漫游网站      联系电话: 152-6096-8634</p>
    </el-footer>
  </div>
</template>

<script>
import Cookies from 'js-cookie';
export default {
  name: 'HomeView',
  data() {
    return {
      // 轮播图图片地址
      carouselImages: [
        require('@/assets/banner1.jpeg'),
        require('@/assets/banner2.jpeg'),
        require('@/assets/banner3.jpeg'),
        require('@/assets/banner4.jpeg'),
        require('@/assets/banner5.jpeg'),
        require('@/assets/banner6.jpeg')
      ],
      
    };
  }
};
</script>

<style scoped>
.custom-carousel {
  width: 100%; /* 设置轮播图框架的宽度 */
  height: 400px; /* 设置轮播图框架的高度 */
  overflow: hidden; /* 超出框架部分隐藏 */
}
.links-link{
  display: flex;
  flex-direction: column;
  margin-top: 25px;
}

.links-link a {
  margin-bottom: 8px;
  color: #a3a3a3;
  font-size: 1.1em;
}
</style>