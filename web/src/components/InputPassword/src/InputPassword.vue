<script setup lang="ts">
import { computed, ref, unref, watch } from 'vue'
import { ElInput } from 'element-plus'
import { zxcvbn } from '@zxcvbn-ts/core'
import type { ZxcvbnResult } from '@zxcvbn-ts/core'
import { useDesign } from '@/hooks/web/useDesign'
import { propTypes } from '@/utils/propTypes'

const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('input-password')

const emit = defineEmits(['update:modelValue'])

const props = defineProps({
  // 是否显示密码强度
  strength: propTypes.bool.def(false),
  modelValue: propTypes.string.def('')
})

// 输入框的值
const valueRef = ref(props.modelValue)
// 监听入参 modelValue 改变更新 valueRef
watch(
  () => props.modelValue,
  (val: string) => {
    if (val === unref(valueRef)) return
    valueRef.value = val
  }
)
watch(
  () => valueRef.value,
  (val: string) => {
    emit('update:modelValue', val)
  }
)

// 设置input的type属性
const textType = ref<'password' | 'text'>('password')
const getIconName = computed(() =>
  unref(textType) === 'password' ? 'ant-design:eye-invisible-outlined' : 'ant-design:eye-outlined'
)
const changeTextType = () => {
  textType.value = unref(textType) === 'text' ? 'password' : 'text'
}

// 获取密码强度
const getPasswordStrength = computed(() => {
  const value = unref(valueRef)
  const zxcvbnRef = zxcvbn(value) as ZxcvbnResult
  return value ? zxcvbnRef.score : -1
})
</script>

<template>
  <div :class="[prefixCls]">
    <ElInput v-bind="$attrs" v-model="valueRef" :type="textType">
      <template #suffix>
        <Icon class="el-input__icon cursor-pointer" :icon="getIconName" @click="changeTextType" />
      </template>
    </ElInput>
    <div
      v-if="strength"
      :class="`${prefixCls}__bar`"
      class="relative h-2 mt-2 mb-2 mr-auto ml-auto"
    >
      <div :class="`${prefixCls}__bar--fill`" :data-score="getPasswordStrength"></div>
    </div>
  </div>
</template>

<style lang="less" scoped>
@prefix-cls: ~'@{namespace}-input-password';

.@{prefix-cls} {
  :deep(.@{elNamespace}-input__clear) {
    margin-left: 5px;
  }

  &__bar {
    background-color: var(--el-text-color-disabled);
    border-radius: var(--el-border-radius-base);

    &::before,
    &::after {
      position: absolute;
      z-index: 10;
      display: block;
      width: 20%;
      height: inherit;
      background-color: transparent;
      border-color: var(--el-color-white);
      border-style: solid;
      border-width: 0 5px 0 5px;
      content: '';
    }

    &::before {
      left: 20%;
    }

    &::after {
      right: 20%;
    }

    &--fill {
      position: absolute;
      width: 0;
      height: inherit;
      background-color: transparent;
      border-radius: inherit;
      transition: width 0.5s ease-in-out, background 0.25s;

      &[data-score='0'] {
        width: 20%;
        background-color: var(--el-color-danger);
      }

      &[data-score='1'] {
        width: 40%;
        background-color: var(--el-color-danger);
      }

      &[data-score='2'] {
        width: 60%;
        background-color: var(--el-color-warning);
      }

      &[data-score='3'] {
        width: 80%;
        background-color: var(--el-color-success);
      }

      &[data-score='4'] {
        width: 100%;
        background-color: var(--el-color-success);
      }
    }
  }

  &--mini > &__bar {
    border-radius: var(--el-border-radius-small);
  }
}
</style>
