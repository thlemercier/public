<template>
    <div class="vg-cell-container" :class="{'group-start': groupStart}">
      <div class="sort-btn" v-if="sort" @click="doSort">
        <arrow-up v-if="toSort === 'asc'"></arrow-up>
        <arrow-down v-if="toSort === 'desc'"></arrow-down>
      </div>
      <div class="cell-content">
        <slot></slot>
      </div>
      <div class="filter-btn" v-if="filterKey" @click="showFilter = true">
        <filter-ar></filter-ar>
      </div>
      <div v-if="showFilter" class="filter-input">
        <fio-input
          v-model="filter" color="primary" placeholder="site.com"
          clearable @input="doFilter" :noBorder="true" />
        <div class="filter-close" @click="showFilter = false">
          X
        </div>
      </div>
    </div>
</template>

<script>
import ArrowUp from './arrow-up.component';
import ArrowDown from './arrow-down.component';
import FilterAr from './icon/filter.component';
import FioInput from 'src/components/core/Input';
export default {
  props: ['groupStart', 'sticky', 'sort', 'filterKey'],
  data () {
    return {
      toSort: this.sort,
      showFilter: false,
      filter: null
    };
  },
  methods: {
    doSort () {
      this.toSort = this.toSort === 'asc' ? 'desc' : 'asc';
      this.$parent.$emit('doSort', {order: this.toSort, key: this.filterKey});
    },
    doFilter (event) {
      this.$parent.$emit('doFilter', {filter: event, key: this.filterKey});
    }
  },
  components: {
    ArrowUp,
    ArrowDown,
    FilterAr,
    FioInput
  },
  mounted () {
    if (this.sticky) {
      this.$nextTick(() => {
        this.$el.style.left = (this.$el.offsetLeft - this.$parent.$el.offsetLeft) + 'px';
        this.$el.classList.add('sticky');
      });
    }
  }
};
</script>

<style>
    .vg-cell-container {
      overflow: auto;
      padding: 5px;
      position: relative;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .vg-cell-container.sticky {
      z-index: 10;
      position: sticky;
      background: #3C3C46;
    }
    .vg-cell-container.group-start {
      border-left: 1px solid rgba(255, 255, 255, 0.12);
      padding-left: 16px;
      justify-content: flex-start;
    }
    .cell-content {
      justify-self: center;
      margin-right: auto;
      flex: auto;
      z-index: 90;
    }
    .sort-btn {
      width: 18px;
      height: 18px;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      justify-self: start;
      z-index: 90;
    }
    .filter-btn {
      width: 18px;
      height: 18px;
      display: flex;
      justify-content: flex-end;
      align-items: center;
      cursor: pointer;
      z-index: 90;
    }
    .filter-input {
      position: absolute;
      width: calc(100% - 20px);
      height: 75%;
      background: rgb(255, 255, 255);
      z-index: 100;
      padding: 0 3px 0 3px;
    }
    .filter-close {
      position: absolute;
      right: 0;
      top: 0;
      z-index: 200;
      background: rgb(184, 184, 184);
      width: 28px;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }
</style>
