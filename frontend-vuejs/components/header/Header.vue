<template>
  <header class="app-header-container q-title">
    <p>test</p>
  </header>
</template>

<script>
export default {
  name: 'AppHeader'
};
</script>

<style lang="stylus" scoped>
  @import '~variables';
  .app-header-container {
    background: $primary;
    height: 75px;
    width: 100%;
    background: $default-yellow;
    color: $darker-grey;
  }
</style>
