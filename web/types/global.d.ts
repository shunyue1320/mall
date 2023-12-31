declare global {
  declare type LocaleType = 'zh-CN' | 'en'

  declare type ElRef<T extends HTMLElement = HTMLDivElement> = Nullable<T>

  declare type Recordable<T = any, K = string> = Record<K extends null | undefined ? string : K, T>

  declare type ComponentRef<T> = InstanceType<T>

  declare interface IResponse<T = any> {
    code: string
    message?: string
    data: T extends any ? T : T & any
  }

  declare type AxiosHeaders =
    | 'application/json'
    | 'application/x-www-form-urlencoded'
    | 'multipart/form-data'
}
