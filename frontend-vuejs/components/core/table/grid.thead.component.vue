<template>
    <div class="vg-thead-container">
        <slot></slot>
    </div>
</template>

<script>
export default {
  props: {
    sticky: {
      type: Boolean,
      default: false
    }
  },
  mounted () {
    if (this.sticky) {
      this.$el.style.setProperty('position', 'sticky');
      this.$el.style.setProperty('top', '0');
      this.$el.style.setProperty('z-index', '10');
    }
    this.$on('doSort', (e) => {
      this.$parent.$emit('doSort', e);
    });
    this.$on('doFilter', (e) => {
      this.$parent.$emit('doFilter', e);
    });
  }
};
</script>

<style>
    .vg-thead-container {
        display: grid;
        grid-template-columns: 1fr;
        color: rgba(255, 255, 255, 0.6);
    }
</style>
