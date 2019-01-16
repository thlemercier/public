<template>
  <div class="flex content-center">
    <q-btn
      :class="`bg-${bgColor} text-${textColor} h-${size}`" :icon="icon" :label="label"
      @click="$emit('click')" @hover="$emit('hover')" />
  </div>
</template>

<script>
export default {
  props: {
    bgColor: {
      type: String,
      default: 'primary'
    },
    textColor: {
      type: String,
      default: 'primary'
    },
    icon: {
      type: String,
      default: null
    },
    label: {
      type: String,
      default: null
    },
    size: {
      type: String,
      default: 'md' // sm md lg
    }
  }
};
</script>

<style lang="stylus" scoped>
  @import '~variables';
  .bg {
    &-primary {
      background: $darker-grey !important;
    }
    &-secondary {
      background: $ligther-grey !important;
    }
    &-danger {
      background: $default-red !important;
    }
    &-warning {
      background: $default-yellow !important;
    }
    &-neutral {
      background: $default-blue !important;
    }
  }
  .text {
    &-primary {
      color: $default-white !important;
    }
    &-secondary {
      color: $darker-grey !important;
    }
    &-danger {
      color: $default-white !important;
    }
    &-warning {
      color: $darker-grey !important;
    }
    &-neutral {
      color: $ligther-grey !important;
    }
  }
  .h {
    align-self: center;
    &-sm {
      font-size: 10px;
    }
    &-md {
      font-size: 12px;
    }
    &-lg {
      font-size: 15px;
    }
  }
</style>
