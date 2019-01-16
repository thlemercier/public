<template>
  <section id="app-layout" class="column">
    <fio-header />
    <fio-navigation />
    <router-view />
  </section>
</template>

<script>
import FioHeader from 'src/components/header/Header';

export default {
  name: 'MyLayout',
  components: {
    FioHeader
  }
};
</script>

<style lang="stylus">
  @import '~variables';
  #app-layout {
    overflow: auto;
    flex: 1;
    font-family: Graphik !important;
    background: $default-grey;
    color: $default-white;
  }
</style>
