/// <reference types="vite/client" />

//  TypeScript 中的 .vue 文件添加类型声明
declare module '*.vue' {
  import { DefineComponent } from 'vue'
  // eslint-disable-next-line @typescript-eslint/no-explicit-any, @typescript-eslint/ban-types
  const component: DefineComponent<{}, {}, any>
  export default component
}

interface ImportMetaEnv {
  readonly VITE_APP_TITLE: string
  readonly VITE_API_BASEPATH: string
  readonly VITE_BASE_PATH: string
}

declare global {
  interface ImportMeta {
    readonly env: ImportMetaEnv
  }
}
