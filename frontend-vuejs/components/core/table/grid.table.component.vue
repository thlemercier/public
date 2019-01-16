<template>
    <div class="vg-table-container">
        <slot></slot>
    </div>
</template>

<script>
export default {
  props: ['vgData'],
  data () {
    return {
      localData: []
    };
  },
  mounted () {
    this.localData = this.vgData;
    this.$on('doSort', (e) => {
      if (e.order === 'desc') {
        this.vgData = this.vgData.sort(function (a, b) {
          return typeof a[e.key] === 'string' ? b[e.key] > a[e.key] : a[e.key] - b[e.key];
        });
      } else {
        this.vgData = this.vgData.sort(function (a, b) {
          return typeof a[e.key] === 'string' ? a[e.key] > b[e.key] : b[e.key] - a[e.key];
        });
      }
      this.$emit('update:vgData', this.vgData);
    });
    this.$on('doFilter', (e) => {
      const data = this.localData.filter(d => {
        return typeof d[e.key] === 'string' ? d[e.key].indexOf(e.filter) !== -1 : `${d[e.key]}`.indexOf(`${e.filter}`) !== -1;
      });
      this.$emit('update:vgData', data);
    });
  }
};
</script>

<style>
    .vg-table-container {
        width: 100%;
        height: 100%;
        overflow: auto;
        font-family: 'Roboto Condensed';
        font-size: 14px;
    }
</style>
