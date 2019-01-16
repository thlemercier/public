<template>
    <div class="vg-row-container" ref="vgRowContainer">
        <slot></slot>
    </div>
</template>

<script>
export default {
  props: {
    height: {
      type: String,
      default: '48px'
    }
  },
  mounted () {
    this.$refs['vgRowContainer'].style.setProperty('grid-template-columns', this.gridColumns);
    this.$el.style.setProperty('grid-template-rows', 'minmax(' + this.height + ', auto)');
    this.$on('doSort', (e) => {
      this.$parent.$emit('doSort', e);
    });
    this.$on('doFilter', (e) => {
      this.$parent.$emit('doFilter', e);
    });
  },
  computed: {
    gridColumns () {
      let columns = [];
      this.$slots.default.forEach(s => {
        if (!s.data.attrs || (!s.data.attrs.width && !s.data.attrs.minWidth && !s.data.attrs.maxWidth)) {
          columns.push('1fr');
        } else if (s.data.attrs.width) {
          columns.push(s.data.attrs.width);
        } else {
          const minmax = `minmax(${s.data.attrs.minWidth ? s.data.attrs.minWidth : '1fr'}, ${s.data.attrs.maxWidth ? s.data.attrs.maxWidth : '1fr'})`;
          columns.push(minmax);
        }
      });
      return columns.join(' ');
    }
  }
};
</script>

<style>
    .vg-row-container {
        display: grid;
        z-index: 0;
        border-bottom: 1px solid rgba(255, 255, 255, 0.12);
        background: #3C3C46;
    }
</style>
